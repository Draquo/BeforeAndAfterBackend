package com.mpm.beforeandafter.email.controller;

import com.mpm.beforeandafter.email.dto.ContactFormRequestDto;
import com.mpm.beforeandafter.email.dto.EmailResponseDto;
import com.mpm.beforeandafter.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/emails")
public class EmailController {
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<EmailResponseDto> sendContactEmail(@RequestBody ContactFormRequestDto contactFormDto) {
        return emailService.sendContactEmail(contactFormDto);
    }
}