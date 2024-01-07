package com.mpm.beforeandafter.email.service;

import com.mpm.beforeandafter.email.dto.ContactFormRequestDto;
import com.mpm.beforeandafter.email.dto.EmailResponseDto;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<EmailResponseDto> sendContactEmail(ContactFormRequestDto contactFormDto);

    CompletableFuture<EmailResponseDto> sendRegistrationEmail(String userName, String userEmail);

    CompletableFuture<EmailResponseDto> sendPasswordResetEmail(String userEmail, String token);
}