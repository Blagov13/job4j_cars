package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarEngineSize;
import ru.job4j.cars.repository.CarEngineSizeRepository;

import java.util.Collection;

@AllArgsConstructor
@Service
public class CarEngineSizeService {
    private final CarEngineSizeRepository carEngineSizeRepository;

    public Collection<CarEngineSize> findAll() {
        return carEngineSizeRepository.findAll();
    }
}
