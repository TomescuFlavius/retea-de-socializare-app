package app.users.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record UserCreateRequest(
        @NotBlank
        String username,

        @NotBlank
        String password,

        @Email
        String email,

        @PastOrPresent(message = "Data nu poate fi in viitor")
        LocalDate createdAt)
{}
