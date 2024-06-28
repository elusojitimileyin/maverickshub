package com.maverickstube.maverickshub.utils;

import com.maverickstube.maverickshub.dtos.requests.UploadMediaRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.maverickstube.maverickshub.models.Category.ACTION;

public class TestUtils {
    public static final String TEST_IMAGE_LOCATION = "C:\\Users\\User\\Desktop\\maverickstube\\src\\main\\resources\\static\\download.jpeg";

    public static final String TEST_VIDEO_LOCATION= "C:\\Users\\User\\Desktop\\maverickstube\\maverickstube\\src\\main\\resources\\static\\vid.mp4";



    public static UploadMediaRequest buildUploadMediaRequest(InputStream inputStream) throws IOException {
        UploadMediaRequest request = new UploadMediaRequest();
        MultipartFile file = new MockMultipartFile("movies ", inputStream);
        request.setMediaFile(file);
        request.setUserId(200L);
        request.setDescription("shoot everyone");
        request.setCategory(ACTION);
        return request;
    }
}
