package com.mpm.beforeandafter.image.controller;

import com.mpm.beforeandafter.image.dto.*;
import com.mpm.beforeandafter.image.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateImageResponseDTO createImage(
            @RequestParam("file") MultipartFile file
            , @RequestParam("description") String description
            , @RequestParam("categoryId") Long categoryId
            , @RequestParam("city") String city
            , @RequestParam("userId") Long userId)
    {

        System.out.println("file.getOriginalFilename = " + file.getOriginalFilename());

        String message = "";
        CreateImageRequestDTO createImageRequestDTO = new CreateImageRequestDTO();
        createImageRequestDTO.setDescription(description);
        createImageRequestDTO.setCategoryId(categoryId);
        createImageRequestDTO.setCity(city);
        createImageRequestDTO.setUserId(userId);

        try {
            CreateImageResponseDTO imageResponseDTO = imageService.createImage(file, createImageRequestDTO);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            System.out.println(message);
            return imageResponseDTO;
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            System.out.println(message);
            return null;
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<ImageFilterResponseDTO> getAllImagesByDynamicFilter(
            @RequestParam(required = false) Set<String> categories,
            @RequestParam(required = false) Set<String> cities,
            @RequestParam(required = false) Boolean approvalStatus,
            @RequestParam(required = false) Set<Long> usersId
    ) {
        ImageFilterRequestDTO request = new ImageFilterRequestDTO();
        request.setCategories(categories);
        request.setCities(cities);
        request.setApprovalStatus(approvalStatus);
        request.setUsersId(usersId);

        return imageService.getImagesByDynamicFilter(request);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable("id") Long imageId) {
        imageService.deleteImage(imageId);
    }
}
