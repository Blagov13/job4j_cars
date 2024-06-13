package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBody;

import java.util.Collection;

@AllArgsConstructor
@Repository
public class CarBodyRepository {
    private final CrudRepository crudRepository;

    public Collection<CarBody> findAll() {
        return crudRepository.query("FROM CarBody", CarBody.class);
    }
}
