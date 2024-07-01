package com.maverickstube.maverickshub.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Recipient {
    private String email;
    private String name;
}
