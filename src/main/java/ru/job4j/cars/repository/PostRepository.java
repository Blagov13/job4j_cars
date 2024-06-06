package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    public void save(Post post) {
        crudRepository.run(session -> session.persist(post));
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
        return crudRepository.query("FROM Post p WHERE p.car.name LIKE :brand",
                Post.class, Map.of("brand", "%" + brand + "%"));
    }
}
