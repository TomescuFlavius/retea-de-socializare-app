package app.comments.service;

import app.comments.dtos.CommentCreateRequest;
import app.comments.dtos.CommentResponse;
import app.comments.exceptions.CommentNotFoundException;
import app.comments.mapper.CommentMapper;
import app.comments.model.Comment;
import app.comments.repository.CommentRepository;
import app.photos.model.Photo;
import app.photos.repository.PhotoRepository;
import app.users.model.User;
import app.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentRepository commentRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    public CommentCommandServiceImpl(CommentRepository commentRepository,
                                     PhotoRepository photoRepository,
                                     UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.photoRepository   = photoRepository;
        this.userRepository    = userRepository;
    }

    @Override
    @Transactional
    public CommentResponse createComment(CommentCreateRequest request) {
        Photo photo = photoRepository.findById(request.photoId())
                .orElseThrow(() -> new RuntimeException("Photo not found"));
        User user  = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = CommentMapper.toEntity(request);
        comment.setPhoto(photo);
        comment.setUser(user);
        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentResponse deleteComment(Long commentId) throws CommentNotFoundException {
        if (commentRepository.findById(commentId).isEmpty()) throw new CommentNotFoundException();
        Comment comment = commentRepository.findById(commentId).get();
        CommentResponse response = CommentMapper.toDto(comment);
        commentRepository.delete(comment);
        return response;
    }
}