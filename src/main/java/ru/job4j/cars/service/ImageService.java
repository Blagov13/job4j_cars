package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.ImageDto;
import ru.job4j.cars.model.Image;
import ru.job4j.cars.repository.ImageRepository;
import ru.job4j.cars.utilites.ImageUtil;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageUtil imageUtil;

    public ImageDto getDefaultImageDto() {
        return imageUtil.getImageDto(imageRepository.getDefaultImage());
    }

    public ImageDto getImageDtoById(int id) {
        return imageUtil.getImageDto(imageRepository.findById(id).get());
    }

    public Optional<Image> getImageById(int id) {
        return imageRepository.findById(id);
    }

    public Image saveImage(ImageDto imageDto) {
        return imageUtil.saveImage(imageDto);
    }

    public void deleteImage(Image image) {
        imageRepository.delete(image);
        imageUtil.deleteImage(image);
    }
}
