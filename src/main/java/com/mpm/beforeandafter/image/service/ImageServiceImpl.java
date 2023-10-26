package com.mpm.beforeandafter.image.service;

import com.mpm.beforeandafter.category.repository.CategoryRepository;
import com.mpm.beforeandafter.image.dto.CreateImageRequest;
import com.mpm.beforeandafter.image.model.Image;
import com.mpm.beforeandafter.image.repository.ImageRepository;
import com.mpm.beforeandafter.user.model.StatusType;
import com.mpm.beforeandafter.user.repository.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final UserDAO userDAO;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, CategoryRepository categoryRepository, UserDAO userDAO) {
        this.imageRepository = imageRepository;
        this.categoryRepository = categoryRepository;
        this.userDAO = userDAO;
    }

    @Override
    public List<Image> getAllImagesByApproved(boolean isApproved) {
        return null;
    }

    @Override
    @Transactional

    public Image createImage(CreateImageRequest request) {
        Image image = new Image();
        image.setFile(request.getFile());
        image.setCategory(categoryRepository.getReferenceById(request.getCategoryId()));
        image.setDescription(request.getDescription());
        image.setUser(userDAO.getReferenceById(request.getUserId()));
        image.setStatus(StatusType.IN_REVIEW);
        log.info(image.toString());

       return imageRepository.save(image);
    }
}
