package com.mpm.beforeandafter.favourites.controller;

import com.mpm.beforeandafter.favourites.dto.AddToFavouritesResponseDto;
import com.mpm.beforeandafter.favourites.dto.DeleteFavouriteResponseDto;
import com.mpm.beforeandafter.favourites.dto.GetFavouriteDTO;
import com.mpm.beforeandafter.favourites.service.FavouritesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/favourites")
public class FavouritesController {

    private final FavouritesService favouritesService;

    public FavouritesController(FavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }

    @GetMapping("/users/{userId}")
    public List<GetFavouriteDTO> getFavouritesByUserId(@PathVariable Long userId) {
        return favouritesService.getFavouritesByUserId(userId);
    }

    //TO DO : usunięcie userId ze ścieżki - implementacja wyciągania userId z sesji lub tokena jwt.
    //SKONSULTOWAĆ: przekazywanie imageId w kontekście JWT? Które podejście PathVariable/DTO?
    @PostMapping("/images/{imageId}/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddToFavouritesResponseDto addImageToFavourites(
            @PathVariable Long imageId, @PathVariable Long userId) {
        return favouritesService.addImageToFavourites(imageId, userId);
    }

    @DeleteMapping("images/{imageId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteFavouriteResponseDto deleteFavourite(
            @PathVariable Long imageId, @PathVariable Long userId) {
        return favouritesService.deleteFavourite(imageId, userId);
    }
}