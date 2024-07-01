package com.maverickstube.maverickshub.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.maverickstube.maverickshub.dtos.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.response.MediaResponse;
import com.maverickstube.maverickshub.dtos.response.UpdateMediaResponse;
import com.maverickstube.maverickshub.dtos.response.UploadMediaResponse;
import com.maverickstube.maverickshub.exceptions.MediaUpdatedFailedException;
import com.maverickstube.maverickshub.exceptions.MediaUploadFailedException;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.models.Media;
import com.maverickstube.maverickshub.models.User;
import com.maverickstube.maverickshub.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@AllArgsConstructor
@Slf4j
public class MavericksHubMediaService implements MediaService {

    private final MediaRepository mediaRepository;
    private final Cloudinary cloudinary;
    private  final ModelMapper modelMapper;
    private final UserService userService;
    @Override
    public UploadMediaResponse upload(UploadMediaRequest uploadMediaRequest ) throws UserNotFoundException {
        User user = userService.getById(uploadMediaRequest.getUserId() );
        try {
            Uploader uploader = cloudinary.uploader();

                    Map<?,?> response =
                    uploader.upload(uploadMediaRequest.getMediaFile().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    log.info("cloudinary upload response :: {}", response);
            String url = response.get("url").toString();
            Media media = modelMapper.map(uploadMediaRequest, Media.class);
            media.setUrl(url);
            media.setUploader(user);
            media = mediaRepository.save(media);
            return modelMapper.map(media, UploadMediaResponse.class);

        }catch (IOException exception) {
            throw new MediaUploadFailedException(exception);
        }
    }

    @Override
    public Media getMediaBy(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(()->
                new RuntimeException(String.format("Media with id %s not found", id)));
    }

    @Override
    public UpdateMediaResponse updateMedia(UpdateMediaRequest request) {

        Media media = modelMapper.map(request,Media.class);
        Media savedMedia = mediaRepository.save(media);
        return modelMapper.map(savedMedia, UpdateMediaResponse.class);
    }

    @Override
    public UpdateMediaResponse updates(Long id, JsonPatch jsonPatch) {
       try {
           //1. get target object
           Media media = getMediaBy(id);
           //2. by converting object to jsonNode
           ObjectMapper objectMapper = new ObjectMapper();
           JsonNode mediaNode = objectMapper.convertValue(media, JsonNode.class);
           //3. apply jsonPatch to mediaNode
           mediaNode = jsonPatch.apply(mediaNode);
           //4. convert mediaNode to Media object
           media = objectMapper.convertValue(mediaNode, Media.class);
           //5. save media
           media = mediaRepository.save(media);
           return modelMapper.map(media, UpdateMediaResponse.class);


       }catch(JsonPatchException exception){
           throw new MediaUpdatedFailedException(exception.getMessage());
        }
    }

    @Override
    public List<MediaResponse> getMediaForUser(Long userId) throws UserNotFoundException {
        userService.getById(userId);
        List<Media> media = mediaRepository.findAllMediaFor(userId);
        return media.stream()
                .map(MediaItem->modelMapper.map(MediaItem,MediaResponse.class)).toList();
    }
}
