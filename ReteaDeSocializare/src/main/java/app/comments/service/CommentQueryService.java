package app.comments.service;

import app.comments.dtos.CommentResponse;
import app.comments.dtos.CommentResponseList;
import app.comments.exceptions.CommentNotFoundException;

public interface CommentQueryService {
    CommentResponseList getAllComments() throws CommentNotFoundException;
    CommentResponseList getCommentsByPhotoId(long photoId) throws CommentNotFoundException;
    CommentResponseList getCommentsByUserId(long userId) throws CommentNotFoundException;
    CommentResponse getCommentById(long commentId) throws CommentNotFoundException;
}