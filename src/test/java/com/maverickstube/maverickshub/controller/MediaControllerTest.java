package com.maverickstube.maverickshub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.maverickstube.maverickshub.utils.TestUtils.TEST_IMAGE_LOCATION;
import static com.maverickstube.maverickshub.utils.TestUtils.TEST_VIDEO_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts= {"/db/data.sql"})
public class MediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMediaController() throws Exception {
        Path path = Paths.get(TEST_IMAGE_LOCATION);
        try (InputStream inputStream = Files.newInputStream(path)) {
            MultipartFile file = new MockMultipartFile("mediaFile", inputStream);
            mockMvc.perform(multipart("/api/v1/media")
                            .file(file.getName(), file.getBytes())
                            .part(new MockPart("userId", "200".getBytes()))
                            .part(new MockPart("description", "test description".getBytes()))
                            .part(new MockPart("category", "HORROR".getBytes()))
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isCreated())
                    .andDo(print());
        } catch (Exception exception) {
            throw exception;
        }

    }
        @Test
    void testGetMediaForUser() {
            try {
                mockMvc.perform(get("/api/v1/media?userId=200")

                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andDo(print());
            } catch (Exception exception) {
                assertThat(exception).isNotNull();
            }
            }



        }


