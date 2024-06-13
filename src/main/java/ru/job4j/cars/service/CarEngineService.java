package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarEngine;
import ru.job4j.cars.model.CarEngineSize;
import ru.job4j.cars.model.CarFuelType;
import ru.job4j.cars.repository.CarEngineRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CarEngineService {
    private final CarEngineRepository carEngineRepository;

    public CarEngine save(CarEngine carEngine) {
        return carEngineRepository.save(carEngine);
    }

    public Optional<CarEngine> findByFuelTypeAndSize(CarFuelType carFuelType, CarEngineSize carEngineSize) {
        return carEngineRepository.findByFuelTypeAndSize(carFuelType, carEngineSize);
    }
}
