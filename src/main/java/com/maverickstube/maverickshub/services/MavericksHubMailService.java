package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.config.MailConfig;
import com.maverickstube.maverickshub.dtos.requests.BrevoMailRequest;
import com.maverickstube.maverickshub.dtos.requests.Recipient;
import com.maverickstube.maverickshub.dtos.requests.SendMailRequest;
import com.maverickstube.maverickshub.dtos.requests.Sender;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class MavericksHubMailService implements MailService {

    private  final MailConfig mailConfig;

    @Override
    public String sendEmail(SendMailRequest mailRequest) {
        RestTemplate restTemplate = new RestTemplate();

        String url = mailConfig.getMailApiUrl();
        BrevoMailRequest request = new BrevoMailRequest();
        request.setSender(new Sender());
        request.setSubject(mailRequest.getSubject());
        request.setRecipients(List.of(
                new Recipient(mailRequest.getRecipientEmail(),mailRequest.getRecipientName())));
        request.setContent();
        restTemplate.postForEntity(url,request,);
        return "";
    }


}
