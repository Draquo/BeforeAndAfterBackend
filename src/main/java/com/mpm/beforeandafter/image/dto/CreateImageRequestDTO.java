package com.mpm.beforeandafter.image.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateImageRequestDTO {

    @NotNull(message = "CategoryId is mandatory")
    private Long categoryId;

    @NotNull(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Name city is mandatory")
    private String city;

    @NotNull(message = "UserId is mandatory")
    private Long userId;
}
