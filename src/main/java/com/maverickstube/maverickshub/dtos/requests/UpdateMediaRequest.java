package com.maverickstube.maverickshub.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Setter
@Getter
public class UpdateMediaRequest {

    private Long id;
    @JsonProperty("media url")
//    private String url;
//    @JsonProperty("media description")
    private String description;
    private Category category;
}
