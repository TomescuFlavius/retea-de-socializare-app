package app.photos.service;

import app.photos.dtos.PhotoCreateRequest;
import app.photos.dtos.PhotoResponse;
import app.photos.exceptions.PhotoAlreadyExistException;
import app.photos.exceptions.PhotoNotFoundException;
import app.photos.mapper.PhotoMapper;
import app.photos.model.Photo;
import app.photos.repository.PhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoCommandServiceImpl implements PhotoCommandService {
    PhotoRepository photoRepository;
    public PhotoCommandServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }
    @Override
    public PhotoResponse createPhoto(PhotoCreateRequest photoCreateRequest) throws PhotoAlreadyExistException {
        if(photoRepository.getPhotoByImgUrl(photoCreateRequest.imgUrl()).isPresent()) throw new PhotoAlreadyExistException();
        Photo savedPhoto = photoRepository.save(PhotoMapper.toEntity(photoCreateRequest));
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
