package app.users.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static app.constants.ExceptionConstants.USER_NOT_FOUND_EXCEPTION;
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(USER_NOT_FOUND_EXCEPTION);
    }}
