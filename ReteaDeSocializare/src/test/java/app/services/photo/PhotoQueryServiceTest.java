package app.services.photo;

import app.photos.dtos.PhotoResponse;
import app.photos.dtos.PhotoResponseList;
import app.photos.exceptions.PhotoNotFoundException;
import app.photos.mapper.PhotoMapper;
import app.photos.model.Photo;
import app.photos.repository.PhotoRepository;
import app.photos.service.PhotoQueryService;
import app.photos.service.PhotoQueryServiceImpl;
import app.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhotoQueryServiceTest {
    @Mock
    private PhotoRepository photoRepository;
    private PhotoQueryService photoQueryService;
    @BeforeEach
    public void setUp() {
        photoQueryService=new PhotoQueryServiceImpl(photoRepository);
    }

    @Test
    void getAllPhotos() throws PhotoNotFoundException {
        User user=new User();
        Photo photo=new Photo(1L,"123",1L, LocalDateTime.now(),user);
        Photo photo2=new Photo(2L,"123",1L, LocalDateTime.now(),user);
        List<Photo> photoList=new ArrayList<>();
        photoList.add(photo);
        photoList.add(photo2);
        when(photoRepository.findAll()).thenReturn(photoList);
        PhotoResponseList expected = new PhotoResponseList(PhotoMapper.toDtoList(photoList));
        PhotoResponseList actual=photoQueryService.getAllPhotos();
        assertEquals(expected, actual);
    }

    @Test
    void getPhotoById() throws PhotoNotFoundException {
        User user=new User();
        Photo photo=new Photo(1L,"123",1L, LocalDateTime.now(),user);
        Photo photo2=new Photo(2L,"123",1L, LocalDateTime.now(),user);
        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        PhotoResponse expected = PhotoMapper.toDto(photo);
        PhotoResponse actual=photoQueryService.getPhotoById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getPhotoByImgUrl() throws PhotoNotFoundException {
        User user=new User();
        Photo photo=new Photo(1L,"12",1L, LocalDateTime.now(),user);
        Photo photo2=new Photo(2L,"123",1L, LocalDateTime.now(),user);
        when(photoRepository.getPhotoByImgUrl("12")).thenReturn(Optional.of(photo));
        PhotoResponse expected = PhotoMapper.toDto(photo);
        PhotoResponse actual=photoQueryService.getPhotoByImgUrl("12");
        assertEquals(expected, actual);
    }

    @Test
    void getAllPhotosByUserId() throws PhotoNotFoundException {
        User user=new User();
        Photo photo=new Photo(1L,"123",1L, LocalDateTime.now(),user);
        Photo photo2=new Photo(2L,"123",1L, LocalDateTime.now(),user);
        List<Photo> photoList=new ArrayList<>();
        photoList.add(photo);
        photoList.add(photo2);
        when(photoRepository.getPhotosByUserId(1L)).thenReturn(photoList);
        PhotoResponseList expected = new PhotoResponseList(PhotoMapper.toDtoList(photoList));
        PhotoResponseList actual=photoQueryService.getAllPhotosByUserId(1L);
        assertEquals(expected, actual);
    }
}
