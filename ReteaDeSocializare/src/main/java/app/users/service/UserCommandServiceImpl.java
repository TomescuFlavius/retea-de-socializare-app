package app.users.service;
import app.jwt.JwtTokenProvider;
import app.security.Permissions;
import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserCreateResponse;
import app.users.dtos.UserResponse;
import app.users.dtos.UserUpdateRequest;
import app.users.exceptions.UserAlreadyExistException;
import app.users.exceptions.UserNotFoundException;
import app.users.mapper.UserMapper;
import app.users.model.User;
import app.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    public UserCommandServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
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

    @Transactional
    @Override
    public UserResponse deleteUser(String email) throws UserNotFoundException {
        if (userRepository.findUserByEmail(email).isEmpty())throw new  UserNotFoundException();
        User savedUser=userRepository.findUserByEmail(email).get();
        userRepository.delete(savedUser);
        return UserMapper.toDto(savedUser);
    }

    @Transactional
    @Override
    public UserCreateResponse register(UserCreateRequest userCreateRequest) {
        if (userRepository.findUserByEmail(userCreateRequest.email()).isPresent()) throw new IllegalArgumentException("User already exists");
        User user = User.builder()
                .email(userCreateRequest.email())
                .username(userCreateRequest.username())
                .password(passwordEncoder.encode(userCreateRequest.password()))
                .permissionGroups(Set.of(Permissions.CAR_READ,   Permissions.USER_WRITE, Permissions.USER_READ,Permissions.WRITE_USER_PERMISSION,Permissions.READ_USER_PERMISSION))
                .build();
        User user1=  userRepository.save(user);
        String token= jwtTokenProvider.generateToken(user1);
        return new UserCreateResponse(userCreateRequest.email(),token);
    }

    @Override
    public UserCreateResponse login(UserCreateRequest userCreateRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCreateRequest.email(), userCreateRequest.password()));
        if (userRepository.findUserByEmail(userCreateRequest.email()).isEmpty()) throw new IllegalArgumentException("Email incorrect");
        User user = userRepository.findUserByEmail(userCreateRequest.email()).get();
        return new UserCreateResponse(user.getEmail(),jwtTokenProvider.generateToken(user));
    }
}
