package app.photos.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record PhotoCreateRequest(
        @NotBlank
        String imgUrl,
        @Id
        Long userId,
        @PastOrPresent(message = "Data nu poate fi in viitor")
        LocalDateTime createdAt
)
{}
