package com.cassianodess.mailsender.configurations;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestHeaderAuthenticationProvider extends BasicAuthenticationEntryPoint {

    @Value("${SECRET_USERNAME}")
    private String SECRET_USERNAME;

    @Override
    public void afterPropertiesSet() {
        setRealmName(this.SECRET_USERNAME);
        super.afterPropertiesSet();
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.addHeader("WWW-Authenticate", String.format("Basic realm=%s", this.SECRET_USERNAME));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authException.getMessage());
    }



    
    
}
