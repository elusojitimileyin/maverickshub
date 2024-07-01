package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dtos.requests.SendMailRequest;

public interface MailService   {
    String sendEmail( SendMailRequest mailRequest);
}
