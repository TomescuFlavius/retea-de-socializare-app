package app.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank
        String username,
        @Email
        String email,
        @NotBlank
        String password
)
{}
