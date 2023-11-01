package com.mpm.beforeandafter.image.service;

import com.mpm.beforeandafter.category.model.Category;
import com.mpm.beforeandafter.category.repository.CategoryRepository;
import com.mpm.beforeandafter.exception.ResourceNotFoundException;
import com.mpm.beforeandafter.image.dto.*;
import com.mpm.beforeandafter.image.model.Image;
import com.mpm.beforeandafter.image.repository.ImageRepository;
import com.mpm.beforeandafter.user.model.StatusType;
import com.mpm.beforeandafter.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private static final String IMAGE_NOT_FOUND_LOG_ERROR_MSG =
            "There is no image found with the given ID: {}";
    private static final String IMAGE_NOT_FOUND_EXCEPTION_MSG =
            "There is no image found with the given ID: ";

    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final ImageMapper imageMapper;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, CategoryRepository categoryRepository,
                            UserRepository userRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public List<GetImagesResponseByStatusApprovalDTO> getImagesByApprovalStatus(
            GetImagesRequestByStatusApprovalDTO request) {
        log.debug("Get all images by approval status: {}", request);
        List<Image> allImagesByApprovalStatus = new ArrayList<>();
        boolean approvalStatus = request.isApprovalStatus();
        allImagesByApprovalStatus = imageRepository.findAll().stream()
                .toList()
                .stream()
                .filter(image -> image.isApproved() == approvalStatus)
                .toList();
        log.info("Images by status approval: {}", allImagesByApprovalStatus);

        return imageMapper.mapToGetImagesByStatusApprovalDTO(allImagesByApprovalStatus);
    }

    @Override
    public CreateImageResponseDTO createImage(CreateImageRequestDTO request) {
        log.debug("Creating new Image: {}", request);
        Image image = new Image();
        image.setFile(request.getFile());
        image.setCategory(categoryRepository.getReferenceById(request.getCategoryId()));
        image.setDescription(request.getDescription());
        image.setUser(userRepository.getReferenceById(request.getUserId()));
        image.setStatus(StatusType.TO_REVIEW);
        imageRepository.save(image);
        log.info("New Image created: {}", image);

        return imageMapper.mapToCreateImageDTO(image);
    }

    @Override
    public List<Image> getImagesByCategoryAndCity(Long categoryId, String cityName) {
        Category category = categoryRepository.getReferenceById(categoryId);
        return imageRepository.findImagesByCategoryAndCityName(category, cityName);
    }

    @Override
    public List<GetAllImagesResponseDTO> getAllImages() {
        log.debug("Get all images");
        List<Image> images = imageRepository.findAll();
        log.info("All images: {}", images);
        return imageMapper.mapToGetAllImagesDTO(images);
    }

    @Override
    public void deleteImage(Long imageId) {
        log.debug("Deleting image with id: {}", imageId);

        imageRepository
                .findById(imageId)
                .ifPresentOrElse(image -> {
                    imageRepository.delete(image);
                    log.info("Image with id: {} deleted successfully", imageId);
                }, () -> {
                    log.error(IMAGE_NOT_FOUND_LOG_ERROR_MSG, imageId);
                    throw new ResourceNotFoundException(IMAGE_NOT_FOUND_EXCEPTION_MSG + imageId);
                });
    }
}