package app.controller.photo;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import app.photos.controller.PhotoController;
import app.photos.dtos.PhotoCreateRequest;
import app.photos.dtos.PhotoResponse;
import app.photos.dtos.PhotoResponseList;
import app.photos.service.PhotoCommandService;
import app.photos.service.PhotoQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.List;


@WebMvcTest(controllers = PhotoController.class)
public class PhotoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    PhotoCommandService photoCommandService;
    @MockitoBean
    PhotoQueryService photoQueryService;


    @Test
    void findAllPhotos() throws Exception{
        PhotoResponse photo1 = new PhotoResponse(1L, "234", 2L, LocalDateTime.now());
        PhotoResponse photo2 = new PhotoResponse(2L, "2354", 2L,LocalDateTime.now());
        PhotoResponseList responseList = new PhotoResponseList(List.of(photo1, photo2));

        when(photoQueryService.getAllPhotos()).thenReturn(responseList);

        mockMvc.perform(get("/api/v1/photos/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().authorities(()->"Photo:Read")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photoResponseList[0].id").value(1L))
                .andExpect(jsonPath("$.photoResponseList[1].imgUrl").value("2354"));
    }

    @Test
    void addPhotoTest() throws Exception {
        PhotoCreateRequest request = new PhotoCreateRequest("bsdvs",1L, LocalDateTime.now());
        PhotoResponse response = new PhotoResponse(10L, "bsdvs",1L, LocalDateTime.now());

        when(photoCommandService.createPhoto(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .with(jwt().authorities(()->"Photo:Read")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.imgUrl").value("bsdvs"));
    }

    @Test
    void deletePhotoTest() throws Exception {
        String imgUrl = "bsds";
        PhotoResponse response = new PhotoResponse(1L, "bsds",1L, LocalDateTime.now());

        when(photoCommandService.deletePhoto(imgUrl)).thenReturn(response);

        mockMvc.perform(delete("/api/v1/photos/delete/{imgUrl}", imgUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt().authorities(()->"Photo:Read")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imgUrl").value(imgUrl))
                .andExpect(jsonPath("$.id").value(1L));

        verify(photoCommandService).deletePhoto(imgUrl);
    }
}
