package com.maverickstube.maverickshub.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendMailRequest {
    private String recipientEmail;
    private String subject;
    private String recipientName;
    private String content;
}
