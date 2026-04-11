package app.users.service;

import app.users.dtos.UserResponse;
import app.users.dtos.UserResponseList;
import app.users.exceptions.UserAlreadyExistException;
import app.users.exceptions.UserNotFoundException;
import app.users.mapper.UserMapper;
import app.users.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    public UserRepository userRepository;
    public UserMapper userMapper;
    public UserQueryServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseList findAllUsers(){
        return new UserResponseList(userMapper.toDtoList(userRepository.findAll()));
    }

    @Override
    public UserResponse findUserById(long id) throws UserNotFoundException {
        if (userRepository.findUserById(id).isEmpty()) throw new UserNotFoundException();
        return userMapper.toDto(userRepository.findUserById(id).get());
    }

    @Override
    public UserResponseList findUsersByUsername(String username) throws UserNotFoundException {
        if (userRepository.findUsersByUsername(username).isEmpty()) throw new UserNotFoundException();
        return new UserResponseList(userMapper.toDtoList(userRepository.findUsersByUsername(username))) ;
    }

    @Override
    public UserResponseList findUsersByEmail(String email)  throws UserNotFoundException {
        if (userRepository.findUsersByEmail(email).isEmpty()) throw new UserNotFoundException();
        return new UserResponseList(userMapper.toDtoList(userRepository.findUsersByEmail(email))) ;    }

    @Override
    public UserResponse findUserByEmail(String email) throws UserAlreadyExistException {
        if (userRepository.findUserByEmail(email).isPresent()) throw new UserAlreadyExistException();
        return userMapper.toDto(userRepository.findUserByEmail(email).get());
    }
}
