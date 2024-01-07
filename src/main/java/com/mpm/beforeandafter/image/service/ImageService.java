package com.mpm.beforeandafter.image.service;

import com.mpm.beforeandafter.image.dto.CreateImageRequestDTO;
import com.mpm.beforeandafter.image.dto.CreateImageResponseDTO;
import com.mpm.beforeandafter.image.dto.ImageFilterRequestDTO;
import com.mpm.beforeandafter.image.dto.ImageFilterResponseDTO;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface ImageService {

    Set<ImageFilterResponseDTO> getImagesByDynamicFilter(ImageFilterRequestDTO request);
    void deleteImage(Long imageId);
    CreateImageResponseDTO createImage(MultipartFile file, CreateImageRequestDTO request) throws FileUploadException;
}