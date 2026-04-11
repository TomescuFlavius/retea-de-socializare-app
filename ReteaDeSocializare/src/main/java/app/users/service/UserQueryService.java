package app.users.service;

import app.users.dtos.UserResponse;
import app.users.dtos.UserResponseList;
import app.users.exceptions.UserAlreadyExistException;
import app.users.exceptions.UserNotFoundException;

public interface UserQueryService {
    UserResponseList findAllUsers();
    UserResponse findUserById(long id) throws UserNotFoundException;
    UserResponseList findUsersByUsername(String username)throws UserNotFoundException;
    UserResponseList findUsersByEmail(String email)throws UserNotFoundException;
    UserResponse findUserByEmail(String email) throws UserAlreadyExistException;
}
