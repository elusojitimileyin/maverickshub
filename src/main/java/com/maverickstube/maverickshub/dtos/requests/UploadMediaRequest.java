package com.maverickstube.maverickshub.dtos.requests;

import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UploadMediaRequest {
    private Long userId;
    private MultipartFile mediaFile;
    private String description;
    private Category category;

}
