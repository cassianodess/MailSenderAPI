package com.cassianodess.mailsender.services;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cassianodess.mailsender.models.Email;

@Service
public class EmailService {

    private String gmailSmtp;
    private String gmailUsername;
    private String gmailPassword;
    private Integer gmailPort;

    public EmailService(
            @Value("${GMAIL_SMTP}") String gmailSmtp,
            @Value("${GMAIL_USERNAME}") String gmailUsername,
            @Value("${GMAIL_PASSWORD}") String gmailPassword,
            @Value("${GMAIL_PORT}") Integer gmailPort
        ) {
        this.gmailUsername = gmailUsername;
        this.gmailPassword = gmailPassword;
        this.gmailSmtp = gmailSmtp;
        this.gmailPort = gmailPort;
    }

    public Boolean sendEmail(Email email) {

        try {
            SimpleEmail simpleEmail = new SimpleEmail();
            simpleEmail.setHostName(gmailSmtp);
            simpleEmail.setSmtpPort(gmailPort);
            simpleEmail.setAuthenticator(new DefaultAuthenticator(gmailUsername, gmailPassword));
            simpleEmail.setSSLOnConnect(true);

            simpleEmail.setFrom(gmailUsername);
            simpleEmail.setSubject(email.getSubject());
            simpleEmail.setMsg(email.getMessage());
            simpleEmail.addTo(email.getEmailTo());
            simpleEmail.send();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

}