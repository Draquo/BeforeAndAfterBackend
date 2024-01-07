package com.mpm.beforeandafter.contactdetails.service;

import com.mpm.beforeandafter.contactdetails.dto.ContactDetailsRequestDto;
import com.mpm.beforeandafter.contactdetails.dto.ContactDetailsResponseDto;
import com.mpm.beforeandafter.contactdetails.dto.GetContactDetailsResponseDto;
import com.mpm.beforeandafter.contactdetails.model.ContactDetails;
import com.mpm.beforeandafter.contactdetails.repository.ContactDetailsRepository;
import com.mpm.beforeandafter.exception.ResourceNotFoundException;
import com.mpm.beforeandafter.user.model.User;
import com.mpm.beforeandafter.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ContactDetailsServiceImpl implements ContactDetailsService {
    private final ContactDetailsRepository contactDetailsRepository;
    private final UserRepository userRepository;
    private final ContactDetailsMapper contactDetailsMapper;


    public ContactDetailsServiceImpl(ContactDetailsRepository contactDetailsRepository,
                                     UserRepository userRepository,
                                     ContactDetailsMapper contactDetailsMapper) {
        this.contactDetailsRepository = contactDetailsRepository;
        this.userRepository = userRepository;
        this.contactDetailsMapper = contactDetailsMapper;
    }

    @Override
    public ContactDetailsResponseDto modifiedContactDetails(Long userId,
                                                            ContactDetailsRequestDto request) {
        Optional<ContactDetails> contact = Optional.of(userRepository.getReferenceById(userId))
                .map(contactDetailsRepository::findContactDetailsByUser);

        ContactDetails contactDetails = new ContactDetails();
        if (contact.isPresent()) {
            contactDetails = contact.get();
            updateContactDetailsWithNonNullFields(request, contactDetails);
            ContactDetails savedDetails = contactDetailsRepository.save(contactDetails);
        } else {
            throw new ResourceNotFoundException("Contact details with user id " + userId + " not found");
        }
        return contactDetailsMapper.mapToCreateContactDetailsResponseDto(contactDetails);
    }

    private static void updateContactDetailsWithNonNullFields(ContactDetailsRequestDto request,
                                                              ContactDetails contactDetails) {
        if (request.getStreetName() != null) {
            contactDetails.setStreetName(request.getStreetName());
        }
        if (request.getStreetNumber() != null) {
            contactDetails.setStreetNumber(request.getStreetNumber());
        }
        if (request.getApartNumber() != null) {
            contactDetails.setApartNumber(request.getApartNumber());
        }
        if (request.getPostcode() != null) {
            contactDetails.setPostcode(request.getPostcode());
        }
        if (request.getCityName() != null) {
            contactDetails.setCityName(request.getCityName());
        }
        if (request.getPhoneNumber() != null) {
            contactDetails.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getWebpage() != null) {
            contactDetails.setWebpage(request.getWebpage());
        }
        if (request.getEmail() != null) {
            contactDetails.setEmail(request.getEmail());
        }
    }

    @Override
    public ContactDetailsResponseDto createContactDetails(ContactDetailsRequestDto request) {
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setUser(userRepository.getReferenceById(request.getUserId()));
        contactDetails.setStreetName(request.getStreetName());
        contactDetails.setStreetNumber(request.getStreetNumber());
        contactDetails.setApartNumber(request.getApartNumber());
        contactDetails.setPostcode(request.getPostcode());
        contactDetails.setCityName(request.getCityName());
        contactDetails.setPhoneNumber(request.getPhoneNumber());
        contactDetails.setEmail(request.getEmail());
        contactDetails.setWebpage(request.getWebpage());
        ContactDetails save = contactDetailsRepository.save(contactDetails);
        return contactDetailsMapper.mapToCreateContactDetailsResponseDto(contactDetails);
    }

    @Override
    public GetContactDetailsResponseDto getContactDetailsByUserId(Long userId) {
        log.info("Getting user with id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User not found with given id: " + userId);
            return new ResourceNotFoundException("User not found with given id: " + userId);
        });
        log.info("Successfully fetched user with id: {}", userId);

        log.info("Getting contact details by user with id: {}", userId);
        ContactDetails contactDetails = contactDetailsRepository
                .findContactDetailsByUser(user);
        if (contactDetails == null) {
            log.error("Contact details not found for user with given id: " + userId);
            throw new ResourceNotFoundException(
                    "Contact details not found for user with given id: " + userId);
        }
        log.info("Successfully fetched contact details for user with given id: {}", userId);
        return contactDetailsMapper.mapToGetContactDetailsResponseDto(contactDetails);
    }

    public GetContactDetailsResponseDto createAndGetDefaultContactDetails(Long userId) {
        log.info("Getting user with id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User not found with given id: " + userId);
            return new ResourceNotFoundException("User not found with given id: " + userId);
        });
        log.info("Successfully fetched user with id: {}", userId);

        log.info("Contact details not found for user with given id: {}. Creating new empty contact details.", userId);
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setUser(user);
        contactDetailsRepository.save(contactDetails);
        log.info("Successfully fetched contact details for user with given id: {}", userId);
        return contactDetailsMapper.mapToGetContactDetailsResponseDto(contactDetails);
    }
}
