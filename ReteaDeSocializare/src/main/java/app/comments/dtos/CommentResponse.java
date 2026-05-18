package app.comments.dtos;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String commentText,
        Long photoId,
        Long userId,
        LocalDateTime createdAt
) {}