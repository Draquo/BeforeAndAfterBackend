package com.mpm.beforeandafter.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryNameRequestDto(
        @NotBlank(message = "Category name is mandatory.")
        @Size(min=2, max=25, message="Category name must be between 2 and 25 characters long.")
        String categoryName) {
}