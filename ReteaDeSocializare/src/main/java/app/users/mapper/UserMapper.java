package app.users.mapper;

import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserMapper {
    public User toEntity(UserCreateRequest userCreateRequest) {
        if (userCreateRequest==null) {
            return null;
        }
        return User.builder()
                .username(userCreateRequest.username())
                .password(userCreateRequest.password())
                .email(userCreateRequest.email())
                .createdAt(userCreateRequest.createdAt())
                .build();
    }

    public UserResponse toDto(User user) {
        if (user==null) {return null;}
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getCreatedAt());
    }

    public List<UserResponse> toDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }
}
