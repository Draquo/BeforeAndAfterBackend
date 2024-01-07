package com.mpm.beforeandafter.category.service;

import com.mpm.beforeandafter.category.dto.CategoryNameRequestDto;
import com.mpm.beforeandafter.category.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryNameRequestDto request);

    List<CategoryResponseDto> getCategories();

    CategoryResponseDto getCategoryById(Long categoryId);

    CategoryResponseDto updateCategoryName(Long categoryId, CategoryNameRequestDto request);

    void deleteCategory(Long categoryId);
}