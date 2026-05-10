package app.users.mapper;
import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.model.User;
import java.util.List;

public class UserMapper {
    public static User toEntity(UserCreateRequest userCreateRequest) {
        if (userCreateRequest==null) {
            return null;
        }
        return User.builder()
                .username(userCreateRequest.username())
                .password(userCreateRequest.password())
                .email(userCreateRequest.email())
                .build();
    }

    public static UserResponse toDto(User user) {
        if (user==null) {return null;}
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getCreatedAt());
    }

    public static List<UserResponse> toDtoList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDto)
                .toList();
    }
}
