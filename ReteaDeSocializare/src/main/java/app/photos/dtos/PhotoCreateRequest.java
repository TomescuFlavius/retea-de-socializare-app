package app.photos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record PhotoCreateRequest(
        @NotBlank
        String imgUrl,
        @NotNull(message = "User id is required")
        Long userId,
        @PastOrPresent(message = "Data nu poate fi in viitor")
        LocalDateTime createdAt
)
{}
