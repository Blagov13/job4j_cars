package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarEngineSize;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class CarEngineSizeRepository {

    private final CrudRepository crudRepository;

    public Collection<CarEngineSize> findAll() {
        return crudRepository.query("FROM CarEngineSize", CarEngineSize.class);
    }
}
