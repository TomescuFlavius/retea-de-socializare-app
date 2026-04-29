package app.services.photo;

import app.photos.dtos.PhotoCreateRequest;
import app.photos.dtos.PhotoResponse;
import app.photos.exceptions.PhotoAlreadyExistException;
import app.photos.exceptions.PhotoNotFoundException;
import app.photos.mapper.PhotoMapper;
import app.photos.model.Photo;
import app.photos.repository.PhotoRepository;
import app.photos.service.PhotoCommandService;
import app.photos.service.PhotoCommandServiceImpl;
import app.users.model.User;
import app.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PhotoCommandServiceTest {
    @Mock
    PhotoRepository photoRepository;
    @Mock
    UserRepository userRepository;
    PhotoCommandService photoCommandService;
    @BeforeEach
    void setUp(){
        photoCommandService=new PhotoCommandServiceImpl(photoRepository, userRepository);
    }

    @Test
    void createPhoto() throws PhotoAlreadyExistException {
        User user=new User();
        user.setId(1L);
        PhotoCreateRequest photoCreateRequest=new PhotoCreateRequest("123",1L, LocalDateTime.of(2025,12,10,10,10));
        PhotoResponse expected= new PhotoResponse(1L,"123",1L,LocalDateTime.of(2025,12,10,10,10));
        when(userRepository.findUserById(photoCreateRequest.userId())).thenReturn(Optional.of(user));
        when(photoRepository.getPhotoByImgUrl(photoCreateRequest.imgUrl())).thenReturn(Optional.empty());
        Photo savedPhoto=new Photo(1L,"123",LocalDateTime.of(2025,12,10,10,10),user);
        when(photoRepository.save(any(Photo.class))).thenReturn(savedPhoto);
        PhotoResponse actual=photoCommandService.createPhoto(photoCreateRequest);
        assertEquals(actual,expected);
    }

    @Test
    void deletePhoto() throws PhotoNotFoundException {
        User user=new User();
        user.setId(1L);
        String imgUrl="123";
        Photo photo=new Photo(1L,"123",LocalDateTime.of(2025,12,10,10,10),user);
        when(photoRepository.getPhotoByImgUrl(imgUrl)).thenReturn(Optional.of(photo));
        PhotoResponse photoResponse=photoCommandService.deletePhoto(imgUrl);
        assertEquals(photoResponse,PhotoMapper.toDto(photo));
    }


}
