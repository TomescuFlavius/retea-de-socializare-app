package app.controller.user;

import app.users.controller.UserController;
import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.dtos.UserResponseList;
import app.users.service.UserCommandService;
import app.users.service.UserQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    UserCommandService userCommandService;
    @MockitoBean
    UserQueryService userQueryService;

    @Test
    void getUserByUsername() throws Exception{
        UserResponse userResponse1=new UserResponse(1L,"Name","pass","e@gmail.com",LocalDate.now());
        UserResponse userResponse2=new UserResponse(2L,"Name","pass","e@gmail.com", LocalDate.now());
        List<UserResponse> userResponseList=new ArrayList<>();
        userResponseList.add(userResponse1);
        userResponseList.add(userResponse2);
        UserResponseList list=new UserResponseList(userResponseList);
        when(userQueryService.findAllUsers()).thenReturn(list);
        mockMvc.perform(get("/api/v1/users/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userResponseList.length()").value(2)) // Folosește numele exact din Body
                .andExpect(jsonPath("$.userResponseList[0].id").value(1L));

    }

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        UserCreateRequest request = new UserCreateRequest("AndreiPopescu", "parola123", "andrei@gmail.com", LocalDate.now());
        UserResponse response = new UserResponse(1L, "AndreiPopescu", "parola123", "andrei@gmail.com", LocalDate.now());
        when(userCommandService.createUser(request)).thenReturn(response);
        mockMvc.perform(post("/api/v1/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("AndreiPopescu"))
                .andExpect(jsonPath("$.email").value("andrei@gmail.com"));
    }

}
