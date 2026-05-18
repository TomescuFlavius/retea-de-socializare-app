package app.comments.mapper;

import app.comments.dtos.CommentCreateRequest;
import app.comments.dtos.CommentResponse;
import app.comments.model.Comment;

import java.util.List;

public class CommentMapper {

    public static Comment toEntity(CommentCreateRequest request) {
        if (request == null) return null;
        return Comment.builder()
                .commentText(request.commentText())
                .createdAt(request.createdAt())
                .build();
    }

    public static CommentResponse toDto(Comment comment) {
        if (comment == null) return null;
        Long photoId = comment.getPhoto() != null ? comment.getPhoto().getId() : null;
        Long userId  = comment.getUser()  != null ? comment.getUser().getId()  : null;
        return new CommentResponse(comment.getId(), comment.getCommentText(), photoId, userId, comment.getCreatedAt());
    }

    public static List<CommentResponse> toDtoList(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toDto).toList();
    }
}