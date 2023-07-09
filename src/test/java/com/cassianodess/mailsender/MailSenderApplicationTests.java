package com.cassianodess.mailsender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import com.cassianodess.mailsender.models.Email;
import com.cassianodess.mailsender.models.EmailResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MailSenderApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void sendEmailSuccess() {
		Email email = new Email("email@email.com", "subject", "message");
		EmailResponse response = this.restTemplate.postForObject("/api/send-email", email, EmailResponse.class);

		assertNotNull(response);
		assertEquals(response.getStatus(), HttpStatus.OK.value());
	}

	@Test
	void sendEmailWithMandatoryFieldBlanck() {
		Email email = new Email("", "subject", "message");
		EmailResponse response = this.restTemplate.postForObject("/api/send-email", email, EmailResponse.class);


		assertNotNull(response);
		assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
		
	}


}
