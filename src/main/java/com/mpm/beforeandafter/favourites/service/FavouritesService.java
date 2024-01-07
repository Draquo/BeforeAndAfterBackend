package com.mpm.beforeandafter.favourites.service;

import com.mpm.beforeandafter.favourites.dto.AddToFavouritesResponseDto;
import com.mpm.beforeandafter.favourites.dto.DeleteFavouriteResponseDto;
import com.mpm.beforeandafter.favourites.dto.GetFavouriteDTO;

import java.util.List;

public interface FavouritesService {

    List<GetFavouriteDTO> getFavouritesByUserId(Long userId);
    AddToFavouritesResponseDto addImageToFavourites(Long imageId, Long userId);

    DeleteFavouriteResponseDto deleteFavourite(Long imageId, Long userId);
}