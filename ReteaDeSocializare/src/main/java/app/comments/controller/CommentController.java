package app.comments.controller;

import app.comments.dtos.CommentCreateRequest;
import app.comments.dtos.CommentResponse;
import app.comments.dtos.CommentResponseList;
import app.comments.exceptions.CommentNotFoundException;
import app.comments.service.CommentCommandService;
import app.comments.service.CommentQueryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/comments")
@Slf4j
public class CommentController {

    private final CommentQueryService commentQueryService;
    private final CommentCommandService commentCommandService;

    public CommentController(CommentQueryService commentQueryService,
                             CommentCommandService commentCommandService) {
        this.commentQueryService   = commentQueryService;
        this.commentCommandService = commentCommandService;
    }

    @GetMapping("/all")
    public ResponseEntity<CommentResponseList> findAllComments() {
        log.info("HTTP /api/v1/comments/all");
        return ResponseEntity.status(HttpStatus.OK).body(commentQueryService.getAllComments());
    }

    @GetMapping("/photo/{photoId}")
    public ResponseEntity<CommentResponseList> findByPhoto(@PathVariable long photoId) {
        log.info("HTTP /api/v1/comments/photo/{}", photoId);
        return ResponseEntity.status(HttpStatus.OK).body(commentQueryService.getCommentsByPhotoId(photoId));
    }

    @PostMapping("/add")
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentCreateRequest request) {
        log.info("HTTP /api/v1/comments/add");
        return ResponseEntity.status(HttpStatus.CREATED).body(commentCommandService.createComment(request));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long commentId) throws CommentNotFoundException {
        log.info("HTTP /api/v1/comments/delete/{}", commentId);
        return ResponseEntity.status(HttpStatus.OK).body(commentCommandService.deleteComment(commentId));
    }
}