package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.List;
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
        user.setLogin("test");
        userRepository.create(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void whenUpdateUserThenReturnTrue() {
        User user = new User();
        user.setLogin("test");
        userRepository.create(user);
        user.setLogin("newLogin");
        userRepository.update(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getLogin()).isEqualTo("newLogin");
    }

    @Test
    public void whenDeleteUserThenRepositoryDoesNotHaveSameUser() {
        User user = new User();
        user.setLogin("test");
        userRepository.create(user);
        userRepository.delete(user.getId());
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isNotPresent();
    }

    @Test
    public void whenFindAllOrderByIdThenReturnAllUsersOrdered() {
        User user1 = new User();
        user1.setLogin("test1");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("test2");
        userRepository.create(user2);
        List<User> users = userRepository.findAllOrderById();
        assertThat(users).hasSize(2);
        assertThat(users.get(0).getLogin()).isEqualTo("test1");
        assertThat(users.get(1).getLogin()).isEqualTo("test2");
    }

    @Test
    public void whenFindByLikeLoginThenReturnMatchingUsers() {
        User user1 = new User();
        user1.setLogin("test1");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("anotherTest");
        userRepository.create(user2);
        List<User> users = userRepository.findByLikeLogin("test");
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getLogin()).isEqualTo("test1");
    }

    @Test
    public void whenFindByLoginThenReturnMatchingUser() {
        User user = new User();
        user.setLogin("test");
        userRepository.create(user);
        Optional<User> result = userRepository.findByLogin("test");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }
}