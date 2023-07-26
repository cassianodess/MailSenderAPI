package com.cassianodess.mailsender.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cassianodess.mailsender.models.Email;
import com.cassianodess.mailsender.models.EmailResponse;
import com.cassianodess.mailsender.services.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/send-email")
public class EmailController {

    @Autowired
    private EmailService service;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<EmailResponse> handleInvalidBodyException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new EmailResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @Operation(security = @SecurityRequirement(name = "basicScheme"))
    @PostMapping
    private ResponseEntity<EmailResponse> sendEmail(@RequestBody @Valid Email email) {
        try {
            Boolean emailSent = service.sendEmail(email);
            if(!emailSent) {
                throw new RuntimeException("Fail while sent email.");
            }
            return ResponseEntity.ok(new EmailResponse(HttpStatus.OK.value(), "Email has been sent successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new EmailResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
    
}
