package app.controller.comment;

import app.comments.model.Comment;
import app.comments.repository.CommentRepository;
import app.photos.model.Photo;
import app.photos.repository.PhotoRepository;
import app.users.model.User;
import app.users.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.comments.dtos.CommentCreateRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private CommentRepository commentRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        commentRepository.deleteAll();
        photoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createGetDeleteFlow() throws Exception {
        User user = userRepository.save(new User(null, "john", "pass", "john@ex.com", null));
        Photo photo = photoRepository.save(Photo.builder().imgUrl("img.jpg").createdAt(LocalDateTime.now()).user(user).build());
        CommentCreateRequest req = new CommentCreateRequest("Hello!", photo.getId(), user.getId(), LocalDateTime.now());

        mockMvc.perform(post("/api/v1/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                        .with(jwt().authorities(() -> "Photo:Read")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentText").value("Hello!"));

        mockMvc.perform(get("/api/v1/comments/all")
                        .with(jwt().authorities(() -> "Photo:Read")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentResponseList[0].commentText").value("Hello!"));
    }
}