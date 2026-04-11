package app.users.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String username,
        String password,
        String email,
        LocalDate createdAt
)
{}
