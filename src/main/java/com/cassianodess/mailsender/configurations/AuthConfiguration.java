package com.cassianodess.mailsender.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Value("${SECRET_PASSWORD}")
    private String SECRET_PASSWORD;
    @Value("${SECRET_USERNAME}")
    private String SECRET_USERNAME;

    @Autowired 
    private RequestHeaderAuthenticationProvider authenticationEntryPoint;

    @Bean
    static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(cors -> cors.configure(http))
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
            auth.anyRequest().authenticated();
        })
        .exceptionHandling(exception -> {
            exception.authenticationEntryPoint((request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            });
        })
        .httpBasic(auth -> {
            auth.init(http);
            auth.authenticationEntryPoint(authenticationEntryPoint);
        })
        .build();
    }

   @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
          .inMemoryAuthentication()
          .withUser(this.SECRET_USERNAME)
          .password(passwordEncoder().encode(this.SECRET_PASSWORD))
          .authorities("ROLE_USER");
    }

    @Bean
    OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .components(
                new Components()
                .addSecuritySchemes("basicScheme", new SecurityScheme().type(Type.HTTP).scheme("basic"))
            )
            .info(
                new Info()
                .title("Email Sender API")
                .description("Spring boot application to send email.")
                .version("v1.0.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
            );
    }
}
