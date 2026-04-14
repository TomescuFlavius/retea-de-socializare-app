package app.users.dtos;
import java.time.LocalDate;

public record UserResponse(
        Long id,
        String username,
        String password,
        String email,
        LocalDate createdAt
)
{}
