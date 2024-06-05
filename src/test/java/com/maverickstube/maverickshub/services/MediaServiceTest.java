package com.maverickstube.maverickshub.services;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.maverickstube.maverickshub.dtos.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.response.MediaResponse;
import com.maverickstube.maverickshub.dtos.response.UpdateMediaResponse;
import com.maverickstube.maverickshub.dtos.response.UploadMediaResponse;
import com.maverickstube.maverickshub.models.Category;
import com.maverickstube.maverickshub.models.Media;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.maverickstube.maverickshub.models.Category.*;
import static com.maverickstube.maverickshub.utils.TestUtils.TEST_VIDEO_LOCATION;
import static com.maverickstube.maverickshub.utils.TestUtils.buildUploadMediaRequest;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Sql(scripts= {"/db/data.sql"})
public class MediaServiceTest {
    @Autowired
    private MediaService mediaService;
    @Test
    void UploadMediaTest() {
        Path path = Paths.get(TEST_VIDEO_LOCATION);
        try (var inputStream = Files.newInputStream(path);) {
            UploadMediaRequest request = buildUploadMediaRequest(inputStream);
            UploadMediaResponse uploadMediaResponse = mediaService.upload(request);
            log.info("Upload media response: {}", uploadMediaResponse);
            assertThat(uploadMediaResponse).isNotNull();
            assertThat(uploadMediaResponse.getUrl()).isNotNull();
        }catch (IOException exception){
            assertThat(exception).isNotNull();
        }
    }

    @Test
    void getMediaByIdTest() {
        Media media = mediaService.getMediaBy(100L);
        log.info("FOUND CONTENT -> {}", media);
        assertThat(media).isNotNull();
    }
    @Test
    void getMediaForUserTest() {
        Long userId = 200L;

        List<MediaResponse> mediaList = mediaService.getMediaForUser(userId);

        assertThat(mediaList).hasSize(3);

    }


    @Test
    void updateMediaTest() {
        Media media = mediaService.getMediaBy(100L);
        assertThat(media.getCategory()).isEqualTo(ACTION);
        UpdateMediaRequest request = new UpdateMediaRequest();
        request .setCategory(STEP_MOM);
        request.setId(100L);
        var response = mediaService.updateMedia(request);
        assertThat(response).isNotNull();
        media = mediaService.getMediaBy(100L);
        assertThat(media.getCategory()).isEqualTo(STEP_MOM);
    }


    @Test
    @DisplayName("update media file")
    void  updateMediaTest2() throws JsonPointerException {
        Category category = mediaService.getMediaBy(102L).getCategory();
        assertThat(category).isNotEqualTo(STEP_MOM);

        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/category"),
                        new TextNode(STEP_MOM.name()))


        );
        JsonPatch updateMediaRequest = new JsonPatch(operations);
        UpdateMediaResponse response = mediaService.updates(102L, updateMediaRequest);
        assertThat(response).isNotNull();
        category = mediaService.getMediaBy(102L).getCategory();
        assertThat(category).isEqualTo(STEP_MOM);
    }

}
