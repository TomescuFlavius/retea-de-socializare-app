package app.photos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static app.constants.ExceptionConstants.PHOTO_NOT_FOUND_EXCEPTION;
public class PhotoNotFoundException extends RuntimeException {
    public PhotoNotFoundException() {
        super(PHOTO_NOT_FOUND_EXCEPTION);
    }}
