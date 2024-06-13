package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarTransmission;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class CarTransmissionRepository {
    private final CrudRepository crudRepository;

    public Collection<CarTransmission> findAll() {
        return crudRepository.query("FROM CarTransmission", CarTransmission.class);
    }
}
