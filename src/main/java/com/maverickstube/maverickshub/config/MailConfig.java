package com.maverickstube.maverickshub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {
    @Value("${mail.api.key}")
    private String mailApiKey;

}
