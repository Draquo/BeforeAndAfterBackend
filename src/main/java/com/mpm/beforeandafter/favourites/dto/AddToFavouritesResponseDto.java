package com.mpm.beforeandafter.favourites.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddToFavouritesResponseDto {
    private Long userId;
    private Long imageId;
    private String status;
}