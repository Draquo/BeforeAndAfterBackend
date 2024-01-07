package com.mpm.beforeandafter.email.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "email_logs")
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_address", length = 100)
    @Email(message = "From address must be a valid email format.")
    @Size(max = 100, message = "From address cannot exceed 100 characters.")
    private String fromAddress;

    @Column(name = "to_address", length = 100)
    @Email(message = "To address must be a valid email format.")
    @Size(max = 100, message = "To address cannot exceed 100 characters.")
    private String toAddress;

    @Column(name = "subject", length = 150)
    @Size(max = 150, message = "Subject cannot exceed 150 characters.")
    private String subject;

    @Column(name = "content", columnDefinition = "TEXT")
    @Lob
    private String content;

    @Column(name = "date_sent")
    @PastOrPresent(message = "The date sent must be in the past or present.")
    private LocalDateTime dateSent;

    @Column(name = "success")
    private boolean success;

    @Column(name = "response_status", length = 50)
    @Size(max = 50, message = "Response status cannot exceed 50 characters.")
    private String responseStatus;

    @Column(name = "error_message", length = 250)
    @Size(max = 250, message = "Error message cannot exceed 250 characters.")
    private String errorMessage;
}