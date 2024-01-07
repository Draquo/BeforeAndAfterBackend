package com.mpm.beforeandafter.image.service;

import com.mpm.beforeandafter.image.dto.CreateImageResponseDTO;
import com.mpm.beforeandafter.image.dto.ImageFilterResponseDTO;
import com.mpm.beforeandafter.image.model.Image;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ImageMapper {
    CreateImageResponseDTO mapToCreateImageDTO(Image image) {
        return CreateImageResponseDTO.builder()
                .imageId(image.getId())
                .categoryId(image.getCategory().getId())
                .description(image.getDescription())
                .userId(image.getUser().getId())
                .build();
    }

    Set<ImageFilterResponseDTO> mapGetImageByFilter(Set<Image> images) {
        Set<ImageFilterResponseDTO> result = new HashSet<>();

        for (Image image : images) {
            ImageFilterResponseDTO mappedImage = ImageFilterResponseDTO.builder()
                    .id(image.getId())
                    .file(image.getFile())
                    .categoryId(image.getCategory().getId())
                    .approvalStatus(image.isApproved())
                    .cityName(image.getCityName())
                    .description(image.getDescription())
                    .userId(image.getUser().getId())
                    .build();
            result.add(mappedImage);
        }
        return result;
    }
}
