package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.ImageDto;
import ru.job4j.cars.dto.PostSearchDto;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.utilites.SearchValidator;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final CarBodyService carBodyService;
    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final CarCategoryService carCategoryService;
    private final CarColorService carColorService;
    private final CarEngineSizeService carEngineSizeService;
    private final CarFuelTypeService carFuelTypeService;
    private final CarTransmissionService carTransmissionService;

    private final SearchValidator searchValidator;

    @GetMapping("/all")
    public String getAllPosts(Model model, @ModelAttribute PostSearchDto searchDto, BindingResult br) {
        addFormAttributesToModel(model);
        searchValidator.validate(searchDto, br);
        boolean searchActive = searchIsActiveCheck(br);
        if (!searchActive) {
            model.addAttribute("posts", postService.findAllNotSold());
        }
        return "posts/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        addFormAttributesToModel(model);
        return "posts/create";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Optional<Post> postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено.");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        return "posts/one";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id) {
        Optional<Post> postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено.");
            return "errors/404";
        }
        addFormAttributesToModel(model);
        model.addAttribute("post", postOptional.get());
        return "posts/edit";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post, @RequestParam MultipartFile file, Model model)
            throws IOException {
        Optional<Post> savedPost = postService.save(post,
                new ImageDto(file.getOriginalFilename(), file.getBytes()));
        if (savedPost.isEmpty()) {
            model.addAttribute("message", "Произошла ошибка при создании объявления.");
            return "errors/404";
        }
        return "redirect:/posts/all";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute Post post, @RequestParam MultipartFile file, Model model)
            throws IOException {
        boolean isUpdated = postService.update(post,
                new ImageDto(file.getOriginalFilename(), file.getBytes()));
        if (!isUpdated) {
            model.addAttribute("message", "Произошла ошибка при обновлении объявления.");
            return "errors/404";
        }
        return "redirect:/users/posts";
    }

    private void addFormAttributesToModel(Model model) {
        Collection<CarModel> carModels = carModelService.findAll();
        Map<CarBrand, List<CarModel>> brandsModels = new LinkedHashMap<>();
        carBrandService.findAll().forEach(brand ->
                brandsModels.put(
                        brand,
                        carModels.stream().filter(cm -> cm.getId() == brand.getId()).toList()));
        model.addAttribute("brandsModels", brandsModels);
        model.addAttribute("bodies", carBodyService.findAll());
        model.addAttribute("transmissions", carTransmissionService.findAll());
        model.addAttribute("fuelTypes", carFuelTypeService.findAll());
        model.addAttribute("engineSizes", carEngineSizeService.findAll());
        model.addAttribute("colors", carColorService.findAll());
        model.addAttribute("categories", carCategoryService.findAll());
    }

    private boolean searchIsActiveCheck(BindingResult bindingResult) {
        return bindingResult.getErrorCount() < PostSearchDto.class.getDeclaredFields().length;
    }

    @GetMapping("/selectCar")
    public String selectCar(@RequestParam(name = "carBrandId", required = false) Integer carBrandId,
                            @RequestParam(name = "redirectPage", required = true) String redirectPage,
                            Model model) {
        addFormAttributesToModel(model);
        if (carBrandId != null) {
            List<CarModel> models = (List<CarModel>) carModelService.findByBrandId(carBrandId);
            model.addAttribute("selectedBrandId", carBrandId);
            model.addAttribute("models", models);
        } else {
            model.addAttribute("selectedBrandId", -1);
            model.addAttribute("models", Collections.emptyList());
        }
        return "posts/" + redirectPage;
    }
}