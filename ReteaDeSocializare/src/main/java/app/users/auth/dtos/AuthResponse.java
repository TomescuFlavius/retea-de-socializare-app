package app.users.auth.dtos;

import app.security.Permissions;

import java.util.Set;

public record AuthResponse(
        Long id,
        String username,
        String email,
        Set<Permissions> permissions,
        String token
) {
}
