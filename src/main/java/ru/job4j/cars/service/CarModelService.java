package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.repository.CarModelRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class CarModelService {
    private final CarModelRepository carModelRepository;

    public Collection<CarModel> findAll() {
        return carModelRepository.findAll();
    }

    public CarModel getById(int id) {
        return carModelRepository.getById(id);
    }

    public Collection<CarModel> findByBrandId(int carBrandId) {
        return carModelRepository.findByBrandId(carBrandId);
    }
}
