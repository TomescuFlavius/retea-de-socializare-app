package app.comments.service;

import app.comments.dtos.CommentResponse;
import app.comments.dtos.CommentResponseList;
import app.comments.exceptions.CommentNotFoundException;
import app.comments.mapper.CommentMapper;
import app.comments.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentRepository commentRepository;

    public CommentQueryServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentResponseList getAllComments() throws CommentNotFoundException {
        if (commentRepository.findAll().isEmpty()) throw new CommentNotFoundException();
        return new CommentResponseList(CommentMapper.toDtoList(commentRepository.findAll()));
    }

    @Override
    public CommentResponseList getCommentsByPhotoId(long photoId) throws CommentNotFoundException {
        if (commentRepository.getCommentsByPhoto_Id(photoId).isEmpty()) throw new CommentNotFoundException();
        return new CommentResponseList(CommentMapper.toDtoList(commentRepository.getCommentsByPhoto_Id(photoId)));
    }

    @Override
    public CommentResponseList getCommentsByUserId(long userId) throws CommentNotFoundException {
        if (commentRepository.getCommentsByUser_Id(userId).isEmpty()) throw new CommentNotFoundException();
        return new CommentResponseList(CommentMapper.toDtoList(commentRepository.getCommentsByUser_Id(userId)));
    }

    @Override
    public CommentResponse getCommentById(long commentId) throws CommentNotFoundException {
        if (commentRepository.findById(commentId).isEmpty()) throw new CommentNotFoundException();
        return CommentMapper.toDto(commentRepository.findById(commentId).get());
    }
}