package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBody;
import ru.job4j.cars.repository.CarBodyRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CarBodyService {
    private final CarBodyRepository carBodyRepository;

    public Collection<CarBody> findAll() {
        return carBodyRepository.findAll();
    }
}
