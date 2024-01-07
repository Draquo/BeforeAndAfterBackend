package com.mpm.beforeandafter.email.dto;

public record ContactFormRequestDto(Long offerUserId, String senderName, String senderEmail, String emailContent) {
}