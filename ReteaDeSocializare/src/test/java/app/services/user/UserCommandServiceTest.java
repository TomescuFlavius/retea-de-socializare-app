package app.services.user;

import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.exceptions.UserAlreadyExistException;
import app.users.mapper.UserMapper;
import app.users.model.User;
import app.users.repository.UserRepository;
import app.users.service.UserCommandServiceImpl;
import app.users.service.UserQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserCommandServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserMapper userMapper;
    @Mock
    private UserCommandServiceImpl userCommandServiceImpl;
    @Mock
    private UserQueryServiceImpl userQueryService;


    @BeforeEach
    void setup(){
        userMapper = new UserMapper();
        userCommandServiceImpl= new UserCommandServiceImpl(userMapper, userRepository);
    }

    @Test
    void createUserTest() throws UserAlreadyExistException {
        UserCreateRequest request1=new UserCreateRequest("test","pass","test@gmail.com", LocalDate.now());
        UserResponse expected=new UserResponse(1L,"test","pass","test@gmail.com", LocalDate.now());
        when(userRepository.findUserByEmail(request1.email())).thenReturn(Optional.empty());
        User savedUser = new User(1L, "test", "pass", "test@gmail.com", LocalDate.now());
        lenient().when(userRepository.save(any(User.class))).thenReturn(savedUser);
        UserResponse actual = userCommandServiceImpl.createUser(request1);
        assertEquals(expected,actual);
    }

}
