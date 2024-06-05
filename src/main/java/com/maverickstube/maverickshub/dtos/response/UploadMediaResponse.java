package com.maverickstube.maverickshub.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class UploadMediaResponse {
   private Long id;
   @JsonProperty("media url")
   private String url;
   @JsonProperty("media description")
   private String description;
   private Category category;
}
