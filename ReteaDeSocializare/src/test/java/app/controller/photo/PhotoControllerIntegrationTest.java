package app.controller.photo;

import app.photos.dtos.PhotoCreateRequest;
import app.photos.repository.PhotoRepository;
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

    @BeforeEach
    void cleanDatabase() {
        photoRepository.deleteAll();
    }

    @Test
    void createGetDeleteFlow() throws Exception {
        PhotoCreateRequest createRequest = new PhotoCreateRequest("ex.jpg", 1L, LocalDateTime.now());

        mockMvc.perform(post("/api/v1/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.imgUrl").value("ex.jpg"));

        mockMvc.perform(get("/api/v1/photos/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photoResponseList.length()").value(1));

        mockMvc.perform(delete("/api/v1/photos/delete/{imgUrl}", createRequest.imgUrl()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/photos/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void duplicateCreateReturnsConflict() throws Exception {
        PhotoCreateRequest request = new PhotoCreateRequest("duplicate-url.jpg", 1L, LocalDateTime.now());

        mockMvc.perform(post("/api/v1/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void validationErrorsAreReturned() throws Exception {
        PhotoCreateRequest invalidRequest = new PhotoCreateRequest("", null, null);

        mockMvc.perform(post("/api/v1/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteNonExistentPhotoReturnsNotFound() throws Exception {
        String nonExistentImgUrl = "nu-exista.jpg";

        mockMvc.perform(delete("/api/v1/photos/delete/{imgUrl}", nonExistentImgUrl))
                .andExpect(status().isNotFound());
    }
}