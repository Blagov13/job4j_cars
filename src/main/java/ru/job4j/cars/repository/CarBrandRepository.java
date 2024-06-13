package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBrand;

import java.util.Collection;
import java.util.Map;

@Repository
@AllArgsConstructor
public class CarBrandRepository {
    private final CrudRepository crudRepository;

    public Collection<CarBrand> findAll() {
        return crudRepository.query("from CarBrand", CarBrand.class);
    }

    public CarBrand getById(int id) {
        return crudRepository.one("from CarBrand where id = :fId", CarBrand.class, Map.of("fId", id));
    }
}
