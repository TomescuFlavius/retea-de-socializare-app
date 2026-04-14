package app.photos.service;
import app.photos.dtos.PhotoCreateRequest;
import app.photos.dtos.PhotoResponse;
import app.photos.exceptions.PhotoAlreadyExistException;
import app.photos.exceptions.PhotoNotFoundException;
public interface PhotoCommandService {
    PhotoResponse createPhoto(PhotoCreateRequest photoCreateRequest)throws PhotoAlreadyExistException;
    PhotoResponse deletePhoto(String imgUrl) throws PhotoNotFoundException;

}
