package app.users.service;

import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.dtos.UserUpdateRequest;
import app.users.exceptions.UserAlreadyExistException;
import app.users.exceptions.UserNotFoundException;

public interface UserCommandService {
    UserResponse createUser(UserCreateRequest userCreateRequest) throws UserAlreadyExistException;
    UserResponse updateUser(long id, UserUpdateRequest userUpdateRequest) throws UserNotFoundException;
    UserResponse deleteUser(String username) throws UserNotFoundException;
}
