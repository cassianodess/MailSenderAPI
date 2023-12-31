package com.cassianodess.mailsender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cassianodess.mailsender.models.Email;
import com.cassianodess.mailsender.models.EmailResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MailSenderApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${SECRET_USERNAME}")
	private String USERNAME;
	@Value("${SECRET_PASSWORD}")
	private String PASSWORD;

	@Test
	void testSendEmailSuccess() {
		Email email = new Email("email@email.com", "subject", "message");
		EmailResponse response = this.restTemplate.withBasicAuth(this.USERNAME, this.PASSWORD).postForObject("/api/send-email", email, EmailResponse.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void testSendEmailWithMandatoryFieldBlanck() {
		Email email = new Email("", "subject", "message");
		EmailResponse response = this.restTemplate.withBasicAuth(this.USERNAME, this.PASSWORD).postForObject("/api/send-email", email, EmailResponse.class);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}

	@Test
	void testSendEmailWithInvalidEmailBody() {
		Email email = new Email("email.com", "subject", "message");
		EmailResponse response = this.restTemplate.withBasicAuth(this.USERNAME, this.PASSWORD).postForObject("/api/send-email", email, EmailResponse.class);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}

	@Test
	void testSendEmailWithoutBasicAuth() {
		Email email = new Email("email@email.com", "subject", "message");
		ResponseEntity<EmailResponse> response = this.restTemplate.postForEntity("/api/send-email", email, EmailResponse.class);

		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}


}
