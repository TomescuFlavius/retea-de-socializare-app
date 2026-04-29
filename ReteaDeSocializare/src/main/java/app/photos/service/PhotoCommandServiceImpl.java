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
    public PhotoResponse createPhoto(PhotoCreateRequest photoCreateRequest) throws PhotoAlreadyExistException {
        User user=userRepository.findUserById(photoCreateRequest.userId()).orElseThrow(PhotoNotFoundException::new);
        if(photoRepository.getPhotoByImgUrl(photoCreateRequest.imgUrl()).isPresent()) throw new PhotoAlreadyExistException();
        Photo savedPhoto = photoRepository.save(PhotoMapper.toEntity(photoCreateRequest));
        savedPhoto.setUser(user);
        return PhotoMapper.toDto(savedPhoto);
    }

    @Override
    public PhotoResponse deletePhoto(String imgUrl) throws PhotoNotFoundException {
        if (photoRepository.getPhotoByImgUrl(imgUrl).isEmpty()) throw new PhotoNotFoundException();
        Photo savedPhoto=photoRepository.getPhotoByImgUrl(imgUrl).get();
        photoRepository.delete(savedPhoto);
        return PhotoMapper.toDto(savedPhoto);
    }
}
