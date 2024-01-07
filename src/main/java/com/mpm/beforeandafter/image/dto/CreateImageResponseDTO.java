package com.mpm.beforeandafter.image.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateImageResponseDTO {
    private long imageId;
    private Long categoryId;
    private String description;
    private Long userId;
}
