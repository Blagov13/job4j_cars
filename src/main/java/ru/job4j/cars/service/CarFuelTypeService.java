package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarFuelType;
import ru.job4j.cars.repository.CarFuelTypeRepository;

import java.util.Collection;

@AllArgsConstructor
@Service
public class CarFuelTypeService {
    private final CarFuelTypeRepository carFuelTypeRepository;

    public Collection<CarFuelType> findAll() {
        return carFuelTypeRepository.findAll();
    }
}
