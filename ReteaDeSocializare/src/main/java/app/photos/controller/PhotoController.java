package app.photos.controller;

import app.photos.dtos.PhotoCreateRequest;
import app.photos.dtos.PhotoResponse;
import app.photos.dtos.PhotoResponseList;
import app.photos.exceptions.PhotoNotFoundException;
import app.photos.service.PhotoCommandService;
import app.photos.service.PhotoQueryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/photos")
@Slf4j
public class PhotoController {
    private PhotoQueryService photoQueryService;
    private PhotoCommandService photoCommandService;
    public PhotoController(PhotoQueryService photoQueryService, PhotoCommandService photoCommandService) {
        this.photoQueryService = photoQueryService;
        this.photoCommandService = photoCommandService;
    }

    @GetMapping("/all")
    public ResponseEntity<PhotoResponseList> findAllPhotos() {
        log.info("HTTP /api/v1/photos/all");
        return ResponseEntity.status(HttpStatus.OK).body(photoQueryService.getAllPhotos());
    }

    @PostMapping("/add")
    public ResponseEntity<PhotoResponse> addPhoto(@Valid @RequestBody PhotoCreateRequest photoCreateRequest) {
        log.info("HTTP /api/v1/photos/add");
        return ResponseEntity.status(HttpStatus.CREATED).body(photoCommandService.createPhoto(photoCreateRequest));
    }

    @DeleteMapping("/delete/{imgUrl}")
    public ResponseEntity<PhotoResponse> deletePhoto(@PathVariable String imgUrl) throws PhotoNotFoundException {
        log.info("HTTP /api/v1/photos/delete/{}",imgUrl);
        return ResponseEntity.status(HttpStatus.OK).body(photoCommandService.deletePhoto(imgUrl));
    }
}
