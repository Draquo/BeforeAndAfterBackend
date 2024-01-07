package com.mpm.beforeandafter.image.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ImageFilterRequestDTO {
    private Set<String> categories;
    private Set<String> cities;
    private Boolean approvalStatus;
    private Set<Long> usersId;

}
