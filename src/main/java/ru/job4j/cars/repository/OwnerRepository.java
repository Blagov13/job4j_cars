package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository {
    private final CrudRepository crudRepository;

    public List<Owner> findAll() {
        return crudRepository.query("from Owner", Owner.class);
    }

    public Optional<Owner> findById(int id) {
        return crudRepository.optional("from Owner where id = :fId", Owner.class, Map.of("fId", id));
    }

    public void save(Owner owner) {
        crudRepository.run(session -> session.save(owner));
    }

    public boolean update(Owner owner) {
        try {
            crudRepository.run(session ->
                    session.update(owner));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            crudRepository.run("delete from Owner where id = :fId", Map.of("fId", id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
