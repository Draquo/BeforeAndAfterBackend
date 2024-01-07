package com.mpm.beforeandafter.category.service;

import com.mpm.beforeandafter.category.dto.CategoryNameRequestDto;
import com.mpm.beforeandafter.category.dto.CategoryResponseDto;
import com.mpm.beforeandafter.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public
class CategoryMapper {
    public Category mapToCategoryEntity(CategoryNameRequestDto requestDTO) {
        return Category.builder()
                .name(requestDTO.categoryName())
                .build();
    }

    public CategoryResponseDto mapToCategoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .categoryName(category.getName())
                .build();
    }
}