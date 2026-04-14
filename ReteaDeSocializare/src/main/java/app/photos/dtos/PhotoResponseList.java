package app.photos.dtos;

import app.photos.model.Photo;

import java.util.List;

public record PhotoResponseList (
        List<PhotoResponse> photoResponseList
){
}
