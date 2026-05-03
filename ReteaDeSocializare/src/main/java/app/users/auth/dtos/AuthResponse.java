package app.users.auth.dtos;

import java.util.Set;

public record AuthResponse(
        Long id,
        String username,
        String email,
        Set<Permissions> permissions,
        String token
) {
}
