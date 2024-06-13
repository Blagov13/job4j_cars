package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarTransmission;
import ru.job4j.cars.repository.CarTransmissionRepository;

import java.util.Collection;

@AllArgsConstructor
@Service
public class CarTransmissionService {
    private final CarTransmissionRepository carTransmissionRepository;

    public Collection<CarTransmission> findAll() {
        return carTransmissionRepository.findAll();
    }

}
