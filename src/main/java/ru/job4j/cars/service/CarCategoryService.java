package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarCategory;
import ru.job4j.cars.repository.CarCategoryRepository;

import java.util.Collection;

@AllArgsConstructor
@Service
public class CarCategoryService {
    private final CarCategoryRepository carCategoryRepository;

    public Collection<CarCategory> findAll() {
        return carCategoryRepository.findAll();
    }
}
