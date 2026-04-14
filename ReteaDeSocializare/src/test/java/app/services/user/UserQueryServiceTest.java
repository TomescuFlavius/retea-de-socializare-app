package app.services.user;
import app.users.dtos.UserResponse;
import app.users.dtos.UserResponseList;
import app.users.exceptions.UserNotFoundException;
import app.users.mapper.UserMapper;
import app.users.model.User;
import app.users.repository.UserRepository;
import app.users.service.UserQueryService;
import app.users.service.UserQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserQueryServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserQueryService userQueryService;

    @BeforeEach
    void setUp() {
        userQueryService=new UserQueryServiceImpl(userRepository);
    }

    @Test
    void getAllUsers() throws UserNotFoundException {
        User user1=new User(1L,"nume","parola","email@gmail.com", LocalDate.now());
        User user2=new User(2L,"nume2","parola2","email@gmail.com", LocalDate.now());
        List<User> userList= List.of(user1,user2);
        when(userRepository.findAll()).thenReturn(userList);
        UserResponseList responseList=userQueryService.findAllUsers();
        assertEquals(UserMapper.toDtoList(userList), responseList.userResponseList());
    }
    @Test
    void getUserById() throws UserNotFoundException {
        Long id=1L;
        UserResponse expected=new UserResponse(1L,"nume","parola","email@gmail.com", LocalDate.now());
        User user1=new User(1L,"nume","parola","email@gmail.com", LocalDate.now());
        User user2=new User(12L,"nume","parola","email@gmail.com", LocalDate.now());
        User user3=new User(13L,"nume","parola","email@gmail.com", LocalDate.now());
        List<User> userList= new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        when(userRepository.findUserById(id)).thenReturn(Optional.of(user1));
        UserResponse actual=userQueryService.findUserById(user1.getId());
        assertEquals(expected,actual);
    }

    @Test
    void getUserByEmail() throws UserNotFoundException {
        User user1=new User(1L,"nume","parola","email@gmail.com", LocalDate.now());
        User user2=new User(2L,"nume2","parola2","email@gmail.com", LocalDate.now());
        List<User> userList= List.of(user1,user2);
        when(userRepository.findUsersByEmail("email@gmail.com")).thenReturn(userList);
        UserResponseList userResponseList=userQueryService.findUsersByEmail("email@gmail.com");
        assertEquals(UserMapper.toDtoList(userList), userResponseList.userResponseList());
    }

    @Test
    void getUsersByUsername() throws UserNotFoundException {
        User user1=new User(1L,"nume","parola","email@gmail.com", LocalDate.now());
        User user2=new User(2L,"nume","parola2","email@gmail.com", LocalDate.now());
        List<User> userList= List.of(user1,user2);
        when(userRepository.findUsersByUsername("nume")).thenReturn(userList);
        UserResponseList userResponseList=userQueryService.findUsersByUsername("nume");
        assertEquals(UserMapper.toDtoList(userList), userResponseList.userResponseList());
    }

    @Test
    void getByEmailUserNotFound() throws UserNotFoundException {
        String email="email@test.com";
        User user1=new User(1L,"nume","parola","email1@gmail.com", LocalDate.now());
        User user2=new User(2L,"nume","parola2","email2@gmail.com", LocalDate.now());
        List<User> userList= List.of(user1,user2);
        when(userRepository.findUsersByEmail(email)).thenReturn(Collections.emptyList());
        assertThrows(UserNotFoundException.class,
                ()->userQueryService.findUsersByEmail(email));
    }

    @Test
    void getByUsernameUserNotFound() throws UserNotFoundException {
        String username="test";
        User user1=new User(1L,"nume1","parola","email1@gmail.com", LocalDate.now());
        User user2=new User(2L,"nume2","parola2","email2@gmail.com", LocalDate.now());
        List<User> userList= List.of(user1,user2);
        when(userRepository.findUsersByUsername(username)).thenReturn(Collections.emptyList());
        assertThrows(UserNotFoundException.class,
                ()->userQueryService.findUsersByUsername(username));
    }

}
