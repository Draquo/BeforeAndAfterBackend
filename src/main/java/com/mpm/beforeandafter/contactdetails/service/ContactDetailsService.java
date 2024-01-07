package com.mpm.beforeandafter.contactdetails.service;

import com.mpm.beforeandafter.contactdetails.dto.ContactDetailsRequestDto;
import com.mpm.beforeandafter.contactdetails.dto.ContactDetailsResponseDto;
import com.mpm.beforeandafter.contactdetails.dto.GetContactDetailsResponseDto;

public interface ContactDetailsService {

    ContactDetailsResponseDto modifiedContactDetails(Long userId, ContactDetailsRequestDto request);

    ContactDetailsResponseDto createContactDetails(ContactDetailsRequestDto request);

    GetContactDetailsResponseDto getContactDetailsByUserId(Long userId);

    GetContactDetailsResponseDto createAndGetDefaultContactDetails(Long userId);
}
