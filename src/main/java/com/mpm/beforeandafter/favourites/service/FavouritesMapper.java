package com.mpm.beforeandafter.favourites.service;


import com.mpm.beforeandafter.favourites.dto.AddToFavouritesResponseDto;
import com.mpm.beforeandafter.favourites.dto.DeleteFavouriteResponseDto;
import org.springframework.stereotype.Component;

@Component
class FavouritesMapper {

    AddToFavouritesResponseDto mapToAddedToFavouritesDTO(Long userId, Long imageId,
                                                         String status) {
        return AddToFavouritesResponseDto.builder()
                .userId(userId)
                .imageId(imageId)
                .status(status)
                .build();
    }

    DeleteFavouriteResponseDto mapToDeleteFavouriteDTO(String message) {
        return DeleteFavouriteResponseDto.builder()
                .message(message)
                .build();
    }
}