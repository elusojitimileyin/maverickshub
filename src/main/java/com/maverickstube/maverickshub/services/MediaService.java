package com.maverickstube.maverickshub.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.maverickstube.maverickshub.dtos.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.response.MediaResponse;
import com.maverickstube.maverickshub.dtos.response.UpdateMediaResponse;
import com.maverickstube.maverickshub.dtos.response.UploadMediaResponse;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.models.Media;

import java.util.List;

public interface MediaService {
    UploadMediaResponse upload(UploadMediaRequest uploadMediaRequest) throws UserNotFoundException;

    Media getMediaBy( Long id);

    UpdateMediaResponse updateMedia(UpdateMediaRequest request);

    UpdateMediaResponse updates(Long userId, JsonPatch jsonPatch);

    List<MediaResponse> getMediaForUser(Long userId) throws UserNotFoundException;
}
