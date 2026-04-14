package app.services.photo;

import app.photos.dtos.PhotoCreateRequest;
import app.photos.dtos.PhotoResponse;
import app.photos.exceptions.PhotoAlreadyExistException;
import app.photos.model.Photo;
import app.photos.repository.PhotoRepository;
import app.photos.service.PhotoCommandService;
import app.photos.service.PhotoCommandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PhotoCommandServiceTest {
    @Mock
    PhotoRepository photoRepository;
    PhotoCommandService photoCommandService;
    @BeforeEach
    void setUp(){
        photoCommandService=new PhotoCommandServiceImpl(photoRepository);
    }

    @Test
    void createPhoto() throws PhotoAlreadyExistException {
        PhotoCreateRequest photoCreateRequest=new PhotoCreateRequest("123",1L, LocalDateTime.now());
        PhotoResponse expected= new PhotoResponse(1L,"123",1L,LocalDateTime.now());
        when(photoRepository.existsPhotoByImgUrl(photoCreateRequest.imgUrl())).thenReturn(false);
        Photo savedPhoto=new Photo(1L,"123",1L,LocalDateTime.now());
        when(photoRepository.save(any(Photo.class))).thenReturn(savedPhoto);
        PhotoResponse actual=photoCommandService.createPhoto(photoCreateRequest);
        assertEquals(actual,expected);
    }
}
