package app.comments.service;

import app.comments.dtos.CommentCreateRequest;
import app.comments.dtos.CommentResponse;
import app.comments.exceptions.CommentNotFoundException;

public interface CommentCommandService {
    CommentResponse createComment(CommentCreateRequest request);
    CommentResponse deleteComment(Long commentId) throws CommentNotFoundException;
}