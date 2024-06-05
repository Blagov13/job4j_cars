package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EngineRepository {
    private final CrudRepository crudRepository;

    public List<Engine> findAll() {
        return crudRepository.query("from Engine", Engine.class);
    }

    public Optional<Engine> findById(int id) {
        return crudRepository.optional("from Engine where id = :fId", Engine.class, Map.of("fId", id));
    }

    public void save(Engine engine) {
        crudRepository.run(session -> session.save(engine));
    }

    public boolean update(Engine engine) {
        try {
            crudRepository.run(session ->
                    session.update(engine));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            crudRepository.run("delete from Engine where id = :fId", Map.of("fId", id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
