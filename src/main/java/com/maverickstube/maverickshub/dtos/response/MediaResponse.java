package com.maverickstube.maverickshub.dtos.response;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
@Setter
@Getter
public class MediaResponse {
    private  Long id;
    private  String url;
    private  String description;
    private Category category;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeCreated;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeUpdated;
    private UserResponse user;
}
