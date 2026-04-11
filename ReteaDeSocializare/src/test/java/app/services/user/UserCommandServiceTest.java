package app.services.user;

import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.exceptions.UserAlreadyExistException;
import app.users.model.User;
import app.users.repository.UserRepository;
import app.users.service.UserCommandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserCommandServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserCommandServiceImpl userCommandServiceImpl;


    @BeforeEach
    void setup(){
        userCommandServiceImpl= new UserCommandServiceImpl(userRepository);
    }

    @Test
    void createUserTest() throws UserAlreadyExistException {
        UserCreateRequest request1=new UserCreateRequest("test","pass","test@gmail.com", LocalDate.now());
        UserResponse expected=new UserResponse(1L,"test","pass","test@gmail.com", LocalDate.now());
        when(userRepository.existsUserByUsername(request1.username())).thenReturn(false);
        User savedUser = new User(1L, "test", "pass", "test@gmail.com", LocalDate.now());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        UserResponse actual = userCommandServiceImpl.createUser(request1);
        assertEquals(expected,actual);
    }

}
