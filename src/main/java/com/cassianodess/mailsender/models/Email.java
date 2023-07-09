package com.cassianodess.mailsender.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @NotBlank(message = "mail to is mandatory")
    private String emailTo;
    @NotBlank(message = "mail from is mandatory")
    private String emailFrom;
    @NotBlank(message = "subject is mandatory")
    private String subject;
    @NotBlank(message = "message is mandatory")
    private String message;

}