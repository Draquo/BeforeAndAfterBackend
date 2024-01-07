package com.mpm.beforeandafter.image.repository;

import com.mpm.beforeandafter.category.model.Category;
import com.mpm.beforeandafter.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findImagesByCategoryAndCityName(Category category, String cityName);
}
