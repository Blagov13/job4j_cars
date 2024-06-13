package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarEngine;
import ru.job4j.cars.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {
    private final CrudRepository crudRepository;

    public Optional<User> save(User user) {
        Optional<User> result = Optional.empty();
        if (crudRepository.run(session -> session.save(user))) {
            result = Optional.of(user);
        }
        return result;
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return crudRepository.optional(
                "FROM User WHERE email = :uEmail AND password = :uPassword",
                User.class,
                Map.of("uEmail", email, "uPassword", password)
        );
    }

    public void deleteByEmailAndPassword(String email, String password) {
        crudRepository.run("DELETE FROM User WHERE email = :uEmail AND password = :uPassword",
                Map.of("uEmail", email, "uPassword", password)
        );
    }

    public boolean update(User user) {
        return crudRepository.run(session -> session.update(user));
    }

    public Optional<User> findById(int id) {
        return crudRepository.optional("from User where id = :fId", User.class, Map.of("fId", id));
    }
}
