package app.users.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static app.constants.ExceptionConstants.USER_ALREADY_EXIST_EXCEPTION;
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super(USER_ALREADY_EXIST_EXCEPTION);
    }
}
