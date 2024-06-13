package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {
    private SessionFactory sf;
    private CrudRepository crudRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        sf = new Configuration().configure().buildSessionFactory();
        crudRepository = new CrudRepository(sf);
        userRepository = new UserRepository(crudRepository);
    }

    @AfterEach
    public void tearDown() {
        crudRepository.run(session -> session.createQuery("DELETE FROM User").executeUpdate());
        sf.close();
    }

    @Test
    public void whenCreateUserThenRepositoryHasSameUser() {
        User user = new User();
        user.setName("test");
        userRepository.save(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void whenUpdateUserThenReturnTrue() {
        User user = new User();
        user.setEmail("test");
        userRepository.save(user);
        user.setPassword("password");
        userRepository.update(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test");
    }

    @Test
    public void whenDeleteUserThenRepositoryDoesNotHaveSameUser() {
        User user = new User();
        user.setEmail("test");
        user.setPassword("password");
        userRepository.save(user);
        userRepository.deleteByEmailAndPassword(user.getEmail(), user.getPassword());
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isNotPresent();
    }
}