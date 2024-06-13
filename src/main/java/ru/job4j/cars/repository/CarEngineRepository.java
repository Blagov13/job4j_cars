package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarEngine;
import ru.job4j.cars.model.CarEngineSize;
import ru.job4j.cars.model.CarFuelType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarEngineRepository {
    private final CrudRepository crudRepository;

    public CarEngine save(CarEngine carEngine) {
        crudRepository.run(session -> session.save(carEngine));
        return carEngine;
    }

    public Optional<CarEngine> findByFuelTypeAndSize(CarFuelType carFuelType, CarEngineSize carEngineSize) {
        return crudRepository.optional(
                "FROM CarEngine AS e WHERE e.carFuelType = :fFuel AND e.carEngineSize = :eEngine",
                CarEngine.class,
                Map.of("fFuel", carFuelType, "eEngine", carEngineSize)
        );
    }

    public List<CarEngine> findAll() {
        return crudRepository.query("from CarEngine", CarEngine.class);
    }

    public Optional<CarEngine> findById(int id) {
        return crudRepository.optional("from CarEngine where id = :fId", CarEngine.class, Map.of("fId", id));
    }

    public boolean update(CarEngine carEngine) {
        try {
            crudRepository.run(session ->
                    session.update(carEngine));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            crudRepository.run("delete from CarEngine where id = :fId", Map.of("fId", id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
