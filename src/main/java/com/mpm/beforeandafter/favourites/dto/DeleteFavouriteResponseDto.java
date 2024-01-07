package com.mpm.beforeandafter.favourites.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteFavouriteResponseDto {

    public String message;

    public DeleteFavouriteResponseDto(String message) {
        this.message = message;
    }
}
