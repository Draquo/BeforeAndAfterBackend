package com.mpm.beforeandafter.category.service;

import com.mpm.beforeandafter.category.dto.CategoryNameRequestDto;
import com.mpm.beforeandafter.category.dto.CategoryResponseDto;
import com.mpm.beforeandafter.category.model.Category;
import com.mpm.beforeandafter.category.repository.CategoryRepository;
import com.mpm.beforeandafter.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORY_NOT_FOUND_LOG_ERROR_MSG =
            "There is no category with the given ID: {}";
    private static final String CATEGORY_NOT_FOUND_EXCEPTION_MSG =
            "There is no category with the given ID: ";

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryNameRequestDto request) {
        log.debug("Creating category: {}", request);
        Category category = categoryMapper.mapToCategoryEntity(request);
        category = categoryRepository.save(category);
        log.info("Category created: {}", category);
        return categoryMapper.mapToCategoryResponseDto(category);
    }

    @Override
    public List<CategoryResponseDto> getCategories() {
        log.debug("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        log.info("Getting all categories (count): {}", categories.size());
        return categories.stream()
                .map(categoryMapper::mapToCategoryResponseDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        log.debug("Getting category by id: {}", categoryId);
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> {
                    log.error(CATEGORY_NOT_FOUND_LOG_ERROR_MSG, categoryId);
                    return new ResourceNotFoundException(
                            CATEGORY_NOT_FOUND_EXCEPTION_MSG + categoryId);
                });
        log.info("Successfully fetched category with ID: {}", categoryId);
        return categoryMapper.mapToCategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto updateCategoryName(Long categoryId, CategoryNameRequestDto request) {
        log.debug("Updating category with ID: {} with data: {}", categoryId, request);
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> {
                    log.error(CATEGORY_NOT_FOUND_LOG_ERROR_MSG, categoryId);
                    return new ResourceNotFoundException(
                            CATEGORY_NOT_FOUND_EXCEPTION_MSG + categoryId);
                });
        category.setName(request.categoryName());
        category = categoryRepository.save(category);
        log.info("Category updated: {}", category.getName());
        return categoryMapper.mapToCategoryResponseDto(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        log.debug("Deleting category with id: {}", categoryId);
        categoryRepository
                .findById(categoryId)
                .ifPresentOrElse(category -> {
                            categoryRepository.delete(category);
                            log.info("Category with id: {} deleted successfully", categoryId);
                        },
                        () -> {
                            log.error(CATEGORY_NOT_FOUND_LOG_ERROR_MSG, categoryId);
                            throw new ResourceNotFoundException(
                                    CATEGORY_NOT_FOUND_EXCEPTION_MSG + categoryId);
                        });
    }
}