package com.mpm.beforeandafter.image.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ImageFilterResponseDTO {
    private long id;
    private byte[] file;
    private String cityName;
    private String description;
    private Long categoryId;
    private Long userId;
    private boolean approvalStatus;
}
