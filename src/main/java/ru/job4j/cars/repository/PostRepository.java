package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    public Optional<Post> save(Post post) {
        Optional<Post> result = Optional.empty();
        if (crudRepository.run(session -> session.save(post))) {
            result = Optional.of(post);
        }
        return result;
    }

    public boolean update(Post post) {
        return crudRepository.run(session -> session.update(post));
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional("FROM Post p JOIN FETCH p.priceHistories WHERE p.id = :pId",
                Post.class,
                Map.of("pId", id));
    }

    public List<Post> findAllNotSold() {
        return crudRepository.query("FROM Post WHERE sold = :not", Post.class, Map.of("not", false));
    }

    public List<Post> findAllByUserId(int id) {
        return crudRepository.query("FROM Post WHERE user_id = :uId",
                Post.class,
                Map.of("uId", id));
    }

    public void deleteAllByUser(User user) {
        crudRepository.run("DELETE Post WHERE user = :user", Map.of("user", user));
    }

    public List<Post> findPostsLastDay() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return crudRepository.query("FROM Post p WHERE p.created >= :yesterday",
                Post.class, Map.of("yesterday", yesterday));
    }

    public List<Post> findPostsWithPhotos() {
        return crudRepository.query("FROM Post p WHERE p.car.files IS NOT EMPTY", Post.class);
    }

    public List<Post> findPostByBrand(String brand) {
        return crudRepository.query("FROM Post p WHERE p.car.name = :brand",
                Post.class, Map.of("brand", brand));
    }
}
