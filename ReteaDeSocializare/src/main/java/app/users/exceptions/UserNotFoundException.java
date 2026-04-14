package app.users.exceptions;
import static app.constants.ExceptionConstants.USER_NOT_FOUND_EXCEPTION;
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super(USER_NOT_FOUND_EXCEPTION);
    }}
