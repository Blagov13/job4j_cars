package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

@AllArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;

    public void delete(Car car) {
        carRepository.delete(car);
    }
}
