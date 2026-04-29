package app.photos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static app.constants.ExceptionConstants.PHOTO_ALREADY_EXIST_EXCEPTION;
public class PhotoAlreadyExistException extends RuntimeException {
    public PhotoAlreadyExistException() {
        super(PHOTO_ALREADY_EXIST_EXCEPTION);
    }
}
