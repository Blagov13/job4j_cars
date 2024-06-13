package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarCategory;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class CarCategoryRepository {
    private final CrudRepository crudRepository;

    public Collection<CarCategory> findAll() {
        return crudRepository.query("from CarCategory", CarCategory.class);
    }
}
