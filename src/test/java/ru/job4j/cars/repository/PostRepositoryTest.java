package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.CarEngine;
import ru.job4j.cars.model.CarEngineSize;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostRepositoryTest {
    private SessionFactory sf;
    private PostRepository postRepository;
    private CarRepository carRepository;
    private CarEngineRepository engineRepository;

    @BeforeEach
    public void setUp() {
        sf = new Configuration().configure().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        postRepository = new PostRepository(crudRepository);
        carRepository = new CarRepository(crudRepository);
        engineRepository = new CarEngineRepository(crudRepository);

        CarEngineSize engineSize = new CarEngineSize();
        engineSize.setSize(12);
        CarEngine engine = new CarEngine();
        engine.setCarEngineSize(engineSize);
        engineRepository.save(engine);

        Car car = new Car();
        car.setCarEngine(engine);
        carRepository.save(car);
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Post").executeUpdate();
        session.createQuery("DELETE FROM Car").executeUpdate();
        session.createQuery("DELETE FROM CarEngine").executeUpdate();
        session.getTransaction().commit();
        session.close();
        sf.close();
    }

    @Test
    public void whenFindPostsLastDayThenReturnList() {
        Post post = new Post();
        post.setCreated(LocalDateTime.now().minusHours(3));
        postRepository.save(post);
        List<Post> posts = postRepository.findPostsLastDay();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0)).isEqualTo(post);
    }

    @Test
    public void whenFindPostByBrandThenReturnList() {
        Post post = new Post();
        post.setDescription("Test post");
        post.setCar(carRepository.findAll().get(0));
        postRepository.save(post);

        List<Post> posts = postRepository.findPostByBrand("TestCar");
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getDescription()).isEqualTo("Test post");
    }
}