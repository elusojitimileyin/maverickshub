package com.maverickstube.maverickshub.exceptions;

import java.io.IOException;

public class MediaUploadFailedException extends RuntimeException {
    public MediaUploadFailedException(IOException message) {
        super(message);
    }
}
