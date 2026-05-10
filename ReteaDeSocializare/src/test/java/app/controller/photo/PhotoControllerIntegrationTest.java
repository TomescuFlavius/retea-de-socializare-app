package app.controller.photo;

import app.photos.dtos.PhotoCreateRequest;
import app.photos.repository.PhotoRepository;
import app.users.model.User;
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

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PhotoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        photoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createGetDeleteFlow() throws Exception {
        User savedUser = userRepository.save(new User(null, "john", "pass", "john@example.com", java.time.LocalDate.now()));
        PhotoCreateRequest createRequest = new PhotoCreateRequest("ex.jpg", savedUser.getId(), LocalDateTime.now());

        mockMvc.perform(post("/api/v1/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
                        .with(jwt().authorities(()->"Photo:Read")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.imgUrl").value("ex.jpg"));

        mockMvc.perform(get("/api/v1/photos/all")
                        .with(jwt().authorities(()->"Photo:Read")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photoResponseList.length()").value(1));

        mockMvc.perform(delete("/api/v1/photos/delete/{imgUrl}", createRequest.imgUrl())
                        .with(jwt().authorities(()->"Photo:Read")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/photos/all")
                        .with(jwt().authorities(()->"Photo:Read")))
                .andExpect(status().isNotFound());
    }

    @Test
    void duplicateCreateReturnsConflict() throws Exception {
        User savedUser = userRepository.save(new User(null, "john2", "pass", "john2@example.com", java.time.LocalDate.now()));
        PhotoCreateRequest request = new PhotoCreateRequest("duplicate-url.jpg", savedUser.getId(), LocalDateTime.now());

        mockMvc.perform(post("/api/v1/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(jwt().authorities(()->"Photo:Create")))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(jwt().authorities(()->"Photo:Create")))
                .andExpect(status().isConflict());
    }

    @Test
    void validationErrorsAreReturned() throws Exception {
        PhotoCreateRequest invalidRequest = new PhotoCreateRequest("", null, null);

        mockMvc.perform(post("/api/v1/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                        .with(jwt().authorities(()->"Photo:Read")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteNonExistentPhotoReturnsNotFound() throws Exception {
        String nonExistentImgUrl = "nu-exista.jpg";

        mockMvc.perform(delete("/api/v1/photos/delete/{imgUrl}", nonExistentImgUrl)
                        .with(jwt().authorities(()->"Photo:Delete")))
                .andExpect(status().isNotFound());
    }
}
