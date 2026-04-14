package app.photos.exceptions;

import static app.constants.ExceptionConstants.PHOTO_NOT_FOUND_EXCEPTION;

public class PhotoNotFoundException extends Exception {
    public PhotoNotFoundException() {
        super(PHOTO_NOT_FOUND_EXCEPTION);
    }}
