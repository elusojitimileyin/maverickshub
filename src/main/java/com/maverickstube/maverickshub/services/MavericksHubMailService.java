package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.config.MailConfig;
import com.maverickstube.maverickshub.dtos.requests.BrevoMailRequest;
import com.maverickstube.maverickshub.dtos.requests.Recipient;
import com.maverickstube.maverickshub.dtos.requests.SendMailRequest;
import com.maverickstube.maverickshub.dtos.requests.Sender;
import com.maverickstube.maverickshub.dtos.response.BrevoMailResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

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
        request.setContent(mailRequest.getContent());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("api-key",mailConfig.getMailApiKey());
        headers.set("accept",APPLICATION_JSON.toString());
        RequestEntity<?> httpRequest = new RequestEntity<>(request,headers, HttpMethod.POST, URI.create(url));
        ResponseEntity<BrevoMailResponse> response =
                restTemplate.postForEntity(url,httpRequest, BrevoMailResponse.class);
        if (response.getBody() != null && response.getStatusCode() == HttpStatusCode.valueOf(201)) {
            return "mail sent successfully";
        }
        else throw new RuntimeException("error sending mail");

    }


}
