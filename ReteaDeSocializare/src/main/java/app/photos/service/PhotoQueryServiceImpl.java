package app.photos.service;

import app.photos.dtos.PhotoResponse;
import app.photos.dtos.PhotoResponseList;
import app.photos.exceptions.PhotoNotFoundException;
import app.photos.mapper.PhotoMapper;
import app.photos.repository.PhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoQueryServiceImpl implements PhotoQueryService {

    public PhotoRepository photoRepository;
    public PhotoQueryServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public PhotoResponseList getAllPhotos() throws PhotoNotFoundException {
        if (photoRepository.findAll().isEmpty()) throw new PhotoNotFoundException();
        return new PhotoResponseList(PhotoMapper.toDtoList(photoRepository.findAll()));
    }

    @Override
    public PhotoResponseList getAllPhotosByUserId(long userId) throws PhotoNotFoundException {
        if (photoRepository.getPhotosByUser_Id(userId).isEmpty()) throw new PhotoNotFoundException();
        return new PhotoResponseList(PhotoMapper.toDtoList(photoRepository.getPhotosByUser_Id(userId)));
    }

    @Override
    public PhotoResponse getPhotoById(long photoId) throws PhotoNotFoundException {
        if (photoRepository.findById(photoId).isEmpty()) throw new PhotoNotFoundException();
        return PhotoMapper.toDto(photoRepository.findById(photoId).get());
    }

    @Override
    public PhotoResponse getPhotoByImgUrl(String imgUrl) throws PhotoNotFoundException {
        if (photoRepository.getPhotoByImgUrl(imgUrl).isEmpty()) throw new PhotoNotFoundException();
        return  PhotoMapper.toDto(photoRepository.getPhotoByImgUrl(imgUrl).get());

    }
}
