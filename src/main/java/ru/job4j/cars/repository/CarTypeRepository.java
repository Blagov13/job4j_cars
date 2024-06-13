package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarTypeRepository {
    private final CrudRepository crudRepository;

    public List<CarType> findAll() {
        return crudRepository.query("from CarType", CarType.class);
    }

    public Optional<CarType> findById(int id) {
        return crudRepository.optional("from CarType where id = :fId", CarType.class, Map.of("fId", id));
    }

    public void save(CarType carType) {
        crudRepository.run(session -> session.save(carType));
    }

    public boolean update(CarType carType) {
        try {
            crudRepository.run(session ->
                    session.update(carType));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            crudRepository.run("delete from CarType where id = :fId", Map.of("fId", id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
