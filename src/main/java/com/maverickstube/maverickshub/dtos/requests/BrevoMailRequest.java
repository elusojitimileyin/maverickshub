package com.maverickstube.maverickshub.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter


public class BrevoMailRequest {
    private Sender sender;
    @JsonProperty("to")
    private List<Recipient> recipients;
    private String subject;
    @JsonProperty("html property")
    private String content;
}
