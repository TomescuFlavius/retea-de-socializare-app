package app.comments.exceptions;

import static app.constants.ExceptionConstants.COMMENT_NOT_FOUND_EXCEPTION;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() { super(COMMENT_NOT_FOUND_EXCEPTION); }
}