package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@AllArgsConstructor
public class PriceHistoryRepository {
    private final CrudRepository crudRepository;

    public Set<PriceHistory> getPriceHistoriesByPostId(int id) {
        return new HashSet<>(crudRepository.query("FROM PriceHistory WHERE post_id = :pId",
                PriceHistory.class,
                Map.of("pId", id)));
    }
}
