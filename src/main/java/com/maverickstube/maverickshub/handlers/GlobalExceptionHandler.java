package com.maverickstube.maverickshub.handlers;

import com.maverickstube.maverickshub.exceptions.MediaUploadFailedException;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(MediaUploadFailedException.class)
    @ResponseBody
    public ResponseEntity<?> handleMediaUploadFailed(MediaUploadFailedException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of(
                        "Error", exception.getMessage(),
                        "success", false
                ));
    }
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<?> handleUserNotFound(UserNotFoundException exception){
            return ResponseEntity.status(BAD_REQUEST)
                    .body(Map.of(
                            "error", exception.getMessage(),
                            "success", false
                            ));
}
    }
