package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.config.MailConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MavericksHubMailService implements MailService {
    @Autowired
    private  final MailConfig mailConfig;

    @Override
    public String sendEmail(String email) {
        return "";
    }


}
