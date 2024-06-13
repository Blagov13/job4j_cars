package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.Set;

@AllArgsConstructor
@Service
public class PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;

    public Set<PriceHistory> getPriceHistoriesByPostId(int id) {
        return priceHistoryRepository.getPriceHistoriesByPostId(id);
    }
}
