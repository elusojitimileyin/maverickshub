package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dtos.requests.SendMailRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    void testSendEmail(){
        String email = "tobantu@gmail.com";
        SendMailRequest mailRequest = new SendMailRequest();
        mailRequest.setRecipientEmail(email);
        mailRequest.setSubject("Testing email");
        mailRequest.setRecipientName("john");
        mailRequest.setContent("<p>Hello from the other side</p>");
        String response = mailService.sendEmail(mailRequest);

        assertThat(response).isNotNull();
        assertThat(response).containsIgnoringCase("success");
    }

}
