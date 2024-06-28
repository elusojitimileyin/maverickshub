package com.maverickstube.maverickshub.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    void testSendEmail(){
        String email = "toxkm@gmail.com";
        mailService.sendEmail(email);
    }

}
