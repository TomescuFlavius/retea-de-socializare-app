package app.photos.dtos;
import java.time.LocalDateTime;

public record PhotoResponse (
         Long id,
         String imgUrl,
         Long userId,
         LocalDateTime createdAt
)
{}
