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
    @Transactional
    public UserResponse updateUser(String email, UserUpdateRequest userUpdateRequest) throws UserNotFoundException {
        if (userRepository.findUserByEmail(email).isEmpty())throw new  UserNotFoundException();
        User user=userRepository.findUserByEmail(email).get();
        if (userUpdateRequest.email()!=null && !userUpdateRequest.email().isBlank())
            user.setEmail(userUpdateRequest.email());
        if (userUpdateRequest.password()!=null && !userUpdateRequest.password().isBlank())
            user.setPassword(userUpdateRequest.password());
        if (userUpdateRequest.username()!=null && !userUpdateRequest.username().isBlank())
            user.setUsername(userUpdateRequest.username());
        User savedUser=userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserResponse deleteUser(String email) throws UserNotFoundException {
        if (userRepository.findUserByEmail(email).isEmpty())throw new  UserNotFoundException();
        User savedUser=userRepository.findUserByEmail(email).get();
        userRepository.delete(savedUser);
        return UserMapper.toDto(savedUser);
    }


}
