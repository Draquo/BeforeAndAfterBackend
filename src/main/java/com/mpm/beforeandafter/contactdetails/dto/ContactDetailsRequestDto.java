package com.mpm.beforeandafter.contactdetails.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDetailsRequestDto {
    private String streetName;
    private String streetNumber;
    private String apartNumber;
    private String postcode;
    private String phoneNumber;
    private String email;
    private String webpage;
    private String cityName;
    private long userId;
}
