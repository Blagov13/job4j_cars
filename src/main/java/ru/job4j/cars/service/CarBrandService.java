package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.CarBrandRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CarBrandService {
    private final CarBrandRepository carBrandRepository;

    public Collection<CarBrand> findAll() {
        return carBrandRepository.findAll();
    }

    public CarBrand getById(int id) {
        return carBrandRepository.getById(id);
    }
}
