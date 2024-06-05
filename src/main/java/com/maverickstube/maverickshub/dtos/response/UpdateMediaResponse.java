package com.maverickstube.maverickshub.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.models.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UpdateMediaResponse {
    private Long id;
    private String url;
    private String description;
    private Category category;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;}
