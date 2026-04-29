package app.services.user;
import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.dtos.UserUpdateRequest;
import app.users.exceptions.UserAlreadyExistException;
import app.users.exceptions.UserNotFoundException;
import app.users.model.User;
import app.users.repository.UserRepository;
import app.users.service.UserCommandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
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
        User user=new User();
        UserCreateRequest request1=new UserCreateRequest("test","pass","test@gmail.com", LocalDate.now());
        UserResponse expected=new UserResponse(1L,"test","pass","test@gmail.com", LocalDate.now());
        when(userRepository.existsUserByUsername(request1.username())).thenReturn(false);
        User savedUser = new User(1L, "test", "pass", "test@gmail.com", LocalDate.now());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        UserResponse actual = userCommandServiceImpl.createUser(request1);
        assertEquals(expected,actual);
    }

    @Test
    void updateUserTest() throws UserNotFoundException {

        User user=new User(1L,"test1","pass1","test1@gmail.com", LocalDate.now());
        UserUpdateRequest request1=new UserUpdateRequest("test", "test@gmail.com","pass");
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserResponse expected=new UserResponse(1L,"test","pass","test@gmail.com",LocalDate.now());
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserResponse actual = userCommandServiceImpl.updateUser(user.getEmail(), request1);
        assertEquals(expected,actual);
    }

    @Test
    void deleteUserTest() throws UserNotFoundException {
        String email="test@gmail.com";
        User user=new User(1L,"test","test","test@gmail.com",LocalDate.now());
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        UserResponse expected=new UserResponse(1L,"test","test","test@gmail.com",LocalDate.now());
        UserResponse actual = userCommandServiceImpl.deleteUser(email);
        assertEquals(expected,actual);
    }
}
