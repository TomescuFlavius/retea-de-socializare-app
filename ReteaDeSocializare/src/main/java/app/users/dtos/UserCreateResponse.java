package app.users.dtos;

public record UserCreateResponse (
        String email,
        String token
) {}
