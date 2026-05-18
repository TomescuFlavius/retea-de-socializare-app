package app.comments.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record CommentCreateRequest(
        @NotBlank
        String commentText,
        @NotNull(message = "Photo id is required")
        Long photoId,
        @NotNull(message = "User id is required")
        Long userId,
        @PastOrPresent(message = "Data nu poate fi in viitor")
        LocalDateTime createdAt
) {}