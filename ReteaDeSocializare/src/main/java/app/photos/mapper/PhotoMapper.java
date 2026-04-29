package app.photos.mapper;

import app.photos.dtos.PhotoCreateRequest;
import app.photos.dtos.PhotoResponse;
import app.photos.model.Photo;

import java.util.List;

public class PhotoMapper {
    public static Photo toEntity(PhotoCreateRequest photoCreateRequest) {
        if (photoCreateRequest == null) {
            return null;
        }
        return Photo.builder()
                .imgUrl(photoCreateRequest.imgUrl())
                .createdAt(photoCreateRequest.createdAt())
                .build();
    }
    public static PhotoResponse toDto(Photo photo) {
        if (photo == null) {
            return null;
        }
        Long userId = photo.getUser() != null ? photo.getUser().getId() : null;
        return new PhotoResponse(photo.getId(),  photo.getImgUrl(), userId, photo.getCreatedAt());
    }

    public static List<PhotoResponse> toDtoList(List<Photo> photos) {
        return photos.stream()
                .map(PhotoMapper::toDto)
                .toList();
    }
}
