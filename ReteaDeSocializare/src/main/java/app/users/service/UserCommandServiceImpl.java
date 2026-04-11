package app.users.service;

import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.dtos.UserUpdateRequest;
import app.users.exceptions.UserAlreadyExistException;
import app.users.exceptions.UserNotFoundException;
import app.users.mapper.UserMapper;
import app.users.model.User;
import app.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private UserRepository userRepository;
    public UserCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest userCreateRequest) throws UserAlreadyExistException {
        if (userRepository.existsUserByUsername(userCreateRequest.username()))throw new  UserAlreadyExistException();
        User savedUser= userRepository.save(UserMapper.toEntity(userCreateRequest));
        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserResponse updateUser(long id, UserUpdateRequest userUpdateRequest) throws UserNotFoundException {
        return null;
    }

    @Override
    public UserResponse deleteUser(String username) throws UserNotFoundException {
        return null;
    }


}
