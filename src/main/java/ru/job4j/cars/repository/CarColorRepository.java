package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarColor;
import ru.job4j.cars.model.CarEngine;
import ru.job4j.cars.model.CarEngineSize;
import ru.job4j.cars.model.CarFuelType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarColorRepository {
    private final CrudRepository crudRepository;

    public Collection<CarColor> findAll() {
        return crudRepository.query("FROM CarColor", CarColor.class);
    }
}
