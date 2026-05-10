package app.controller.user;

import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.dtos.UserUpdateRequest;
import app.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Test
    void createGetUpdateDeleteFlow() throws Exception {
        UserCreateRequest createRequest = new UserCreateRequest("JohnDoe", "password123", "john@example.com");

            MvcResult createResult = mockMvc.perform(post("/api/v1/users/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createRequest))
                            .with(jwt().authorities(()->"User:Create")))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.username").value("JohnDoe"))
                    .andReturn();

            UserResponse created = objectMapper.readValue(createResult.getResponse().getContentAsByteArray(), UserResponse.class);

            mockMvc.perform(get("/api/v1/users/all").with(jwt().authorities(()->"User:Read")))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(1));

            UserUpdateRequest updateRequest = new UserUpdateRequest("JohnUpdated", "john@example.com", "newPassword");
            mockMvc.perform(put("/api/v1/users/update/{email}", "john@example.com")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest))
                            .with(jwt().authorities(()->"User:Update")))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("JohnUpdated"));

            mockMvc.perform(delete("/api/v1/users/delete/{email}", "john@example.com")
                            .with(jwt().authorities(()->"User:Delete")))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/api/v1/users/all").with(jwt().authorities(()->"User:Read")))
                    .andExpect(jsonPath("$.userResponseList.length()").value(0));
        }

        @Test
        void duplicateCreateReturnsConflict() throws Exception {
            UserCreateRequest request = new UserCreateRequest("JohnDoe", "pass", "john@email.com");

            mockMvc.perform(post("/api/v1/users/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(jwt().authorities(()->"User:Create")))
                    .andExpect(status().isCreated());

            mockMvc.perform(post("/api/v1/users/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)).with(jwt().authorities(()->"User:Create")))
                    .andExpect(status().isConflict());
        }

        @Test
        void validationErrorsAreReturned() throws Exception {
            UserCreateRequest invalid = new UserCreateRequest("", "", "");

            mockMvc.perform(post("/api/v1/users/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalid)).with(jwt().authorities(()->"User:Create")))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void updateNonExistentUserReturnsNotFound() throws Exception {
            UserUpdateRequest updateRequest = new UserUpdateRequest("NoOne", "pass", "none@email.com");
            String em="email";
            mockMvc.perform(put("/api/v1/users/update/{email}", em)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)).with(jwt().authorities(()->"User:Update")))
                    .andExpect(status().isNotFound());
        }
}
