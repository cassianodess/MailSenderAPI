package com.cassianodess.mailsender.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @jakarta.validation.constraints.Email(message = "email must be valid")
    @NotBlank(message = "email is mandatory")
    @NotEmpty(message = "email must be not empty")
    private String emailTo;
    @NotEmpty(message = "subject must be not empty")
    @NotBlank(message = "subject is mandatory")
    private String subject;
    @NotEmpty(message = "message must be not empty")
    @NotBlank(message = "message is mandatory")
    private String message;

}