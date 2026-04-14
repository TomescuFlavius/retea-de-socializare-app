package app.photos.service;
import app.photos.dtos.PhotoResponse;
import app.photos.dtos.PhotoResponseList;
import app.photos.exceptions.PhotoNotFoundException;

public interface PhotoQueryService {
    PhotoResponseList getAllPhotos() throws PhotoNotFoundException;
    PhotoResponseList getAllPhotosByUserId(long userId) throws PhotoNotFoundException;
    PhotoResponse getPhotoById(long photoId) throws PhotoNotFoundException;
    PhotoResponse getPhotoByImgUrl(String imgUrl) throws PhotoNotFoundException;
}
