package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarFuelType;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class CarFuelTypeRepository {
    private final CrudRepository crudRepository;

    public Collection<CarFuelType> findAll() {
        return crudRepository.query("FROM CarFuelType", CarFuelType.class);
    }
}
