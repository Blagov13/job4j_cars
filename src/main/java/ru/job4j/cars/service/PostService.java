package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.ImageDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final CarModelService carModelService;
    private final CarService carService;
    private final CarEngineService carEngineService;
    private final ImageService imageService;
    private final PriceHistoryService priceHistoryService;

    public Optional<Post> save(Post post, ImageDto imageDto) {
        post.setCreated(LocalDateTime.now());
        setBrand(post.getCar());
        setEngine(post.getCar());
        addPriceHistory(post);
        Image newImage = imageDto.getContent().length != 0 ? imageService.saveImage(imageDto) : null;
        post.setImage(newImage);
        Optional<Post> postOptional = postRepository.save(post);
        if (postOptional.isEmpty() && newImage != null) {
            imageService.deleteImage(newImage);
        }
        return postOptional;
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findAllNotSold() {
        return postRepository.findAllNotSold();
    }

    public List<Post> findAllByUserId(int id) {
        return postRepository.findAllByUserId(id);
    }

    public boolean update(Post post, ImageDto imageDto) {
        setBrand(post.getCar());
        setEngine(post.getCar());
        post.setPriceHistories(priceHistoryService.getPriceHistoriesByPostId(post.getId()));
        PriceHistory lastPrice = Collections.max(post.getPriceHistories(),
                Comparator.comparing(PriceHistory::getDate));
        if (post.getPrice() != lastPrice.getPrice()) {
            addPriceHistory(post);
        }

        boolean isNewImageExists = imageDto.getContent().length != 0;
        Optional<Image> oldImage = post.getImage() != null
                ? imageService.getImageById(post.getImage().getId()) : Optional.empty();
        if (!isNewImageExists) {
            oldImage.ifPresent(post::setImage);
            return postRepository.update(post);
        }
        Image newImage = imageService.saveImage(imageDto);
        post.setImage(newImage);
        boolean isUpdated = postRepository.update(post);
        if (isUpdated) {
            oldImage.ifPresent(imageService::deleteImage);
        } else {
            imageService.deleteImage(newImage);
        }
        return isUpdated;
    }

    public void deleteAllByUser(User user) {
        List<Post> posts = postRepository.findAllByUserId(user.getId());
        postRepository.deleteAllByUser(user);
        posts.forEach(this::deletePostsImage);
        posts.forEach(p -> carService.delete(p.getCar()));
    }

    private void setBrand(Car car) {
        CarModel carModel = carModelService.getById(car.getCarModel().getId());
        CarBrand carBrand = new CarBrand();
        carBrand.setId(carModel.getBrand().getId());
        car.setCarBrand(carBrand);
    }

    private void setEngine(Car car) {
        if (car == null || car.getCarEngine() == null) {
            return;
        }

        CarEngine existingEngine = car.getCarEngine();
        CarEngine engineInDB = carEngineService.findByFuelTypeAndSize(existingEngine.getCarFuelType(), existingEngine.getCarEngineSize())
                .orElseGet(() -> carEngineService.save(existingEngine));

        car.setCarEngine(engineInDB);
    }

    private void deletePostsImage(Post post) {
        Optional<Image> postImage = post.getImage() != null
                ? imageService.getImageById(post.getImage().getId()) : Optional.empty();
        postImage.ifPresent(imageService::deleteImage);
    }

    private void addPriceHistory(Post post) {
        PriceHistory newPriceHistory = new PriceHistory();
        newPriceHistory.setPrice(post.getPrice());
        post.getPriceHistories().add(newPriceHistory);
    }
}
