package com.cassianodess.mailsender.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cassianodess.mailsender.models.Email;
import com.cassianodess.mailsender.models.EmailResponse;
import com.cassianodess.mailsender.services.EmailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/send-email")
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping
    private ResponseEntity<EmailResponse> sendEmail(@RequestBody @Valid Email email) {
        try {
            Boolean emailSent = service.sendEmail(email);
            if(!emailSent) {
                throw new RuntimeException("fail while sent email");
            }
            return ResponseEntity.ok(new EmailResponse(200, "Email has been sent successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new EmailResponse(400, "fail while sent email"));
        }
    }
    
}
