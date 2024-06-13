package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository {
    private final CrudRepository crudRepository;

    public void delete(Car car) {
        crudRepository.run(session -> session.delete(car));
    }

    public List<Car> findAll() {
        return crudRepository.query("from Car", Car.class);
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional("from Car where id = :fId", Car.class, Map.of("fId", id));
    }

    public boolean update(Car car) {
        try {
            crudRepository.run(session ->
                    session.update(car));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            crudRepository.run("delete from Car where id = :fId", Map.of("fId", id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Car save(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }
}
