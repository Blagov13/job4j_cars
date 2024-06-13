package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarModel;

import java.util.Collection;
import java.util.Map;

@Repository
@AllArgsConstructor
public class CarModelRepository {
    private final CrudRepository crudRepository;

    public Collection<CarModel> findAll() {
        return crudRepository.query("FROM CarModel", CarModel.class);
    }

    public CarModel getById(int id) {
        return crudRepository.one("FROM CarModel WHERE id = :fId", CarModel.class, Map.of("fId", id));
    }

    public Collection<CarModel> findByBrandId(int carBrandId) {
        System.out.println("Fetching models for brand ID: " + carBrandId);
        Collection<CarModel> models = crudRepository.query("FROM CarModel WHERE brand.id = :brandId", CarModel.class, Map.of("brandId", carBrandId));
        System.out.println("Models fetched: " + models);
        return models;
    }
}
