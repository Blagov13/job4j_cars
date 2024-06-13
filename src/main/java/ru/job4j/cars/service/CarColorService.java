package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarColor;
import ru.job4j.cars.repository.CarColorRepository;

import java.util.Collection;

@AllArgsConstructor
@Service
public class CarColorService {
    private final CarColorRepository carColorRepository;

    public Collection<CarColor> findAll() {
        return carColorRepository.findAll();
    }
}
