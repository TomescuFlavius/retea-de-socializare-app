package app.photos.service;

import app.photos.dtos.PhotoCreateRequest;
import app.photos.dtos.PhotoResponse;
import app.photos.exceptions.PhotoAlreadyExistException;
import app.photos.exceptions.PhotoNotFoundException;
import app.photos.mapper.PhotoMapper;
import app.photos.model.Photo;
import app.photos.repository.PhotoRepository;
import app.users.model.User;
import app.users.repository.UserRepository;
import app.users.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PhotoCommandServiceImpl implements PhotoCommandService {
    private PhotoRepository photoRepository;
    private UserRepository userRepository;
    public PhotoCommandServiceImpl(PhotoRepository photoRepository,UserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public PhotoResponse createPhoto(PhotoCreateRequest photoCreateRequest) throws PhotoAlreadyExistException {
        User user=userRepository.findUserById(photoCreateRequest.userId()).orElseThrow(UserNotFoundException::new);
        if(photoRepository.getPhotoByImgUrl(photoCreateRequest.imgUrl()).isPresent()) throw new PhotoAlreadyExistException();
        Photo photo = PhotoMapper.toEntity(photoCreateRequest);
        user.addPhoto(photo);
        Photo savedPhoto = photoRepository.save(photo);
        return PhotoMapper.toDto(savedPhoto);
    }

    @Override
    public PhotoResponse deletePhoto(String imgUrl) throws PhotoNotFoundException {
        if (photoRepository.getPhotoByImgUrl(imgUrl).isEmpty()) throw new PhotoNotFoundException();
        Photo savedPhoto=photoRepository.getPhotoByImgUrl(imgUrl).get();
        PhotoResponse response = PhotoMapper.toDto(savedPhoto);
        photoRepository.delete(savedPhoto);
        return response;
    }
}
