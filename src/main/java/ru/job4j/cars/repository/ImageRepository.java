package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Image;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ImageRepository {
    private static final String DEFAULT_PHOTO_NAME = "defaultPhoto.png";

    private final CrudRepository crudRepository;

    public Image getDefaultImage() {
        return crudRepository.one(
                "FROM Image WHERE name = :iName",
                Image.class,
                Map.of("iName", DEFAULT_PHOTO_NAME)
        );
    }

    public Optional<Image> findById(int id) {
        return crudRepository.optional("FROM Image WHERE id = :Id",
                Image.class,
                Map.of("Id", id));
    }

    public void delete(Image image) {
        crudRepository.run(session -> session.delete(image));
    }
}
