package app.services.comment;

import app.comments.dtos.CommentResponse;
import app.comments.dtos.CommentResponseList;
import app.comments.exceptions.CommentNotFoundException;
import app.comments.mapper.CommentMapper;
import app.comments.model.Comment;
import app.comments.repository.CommentRepository;
import app.comments.service.CommentQueryService;
import app.comments.service.CommentQueryServiceImpl;
import app.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentQueryServiceTest {

    @Mock
    private CommentRepository commentRepository;
    private CommentQueryService commentQueryService;

    @BeforeEach
    void setUp() { commentQueryService = new CommentQueryServiceImpl(commentRepository); }

    @Test
    void getAllComments() throws CommentNotFoundException {
        User user = new User(); user.setId(1L);
        Comment c1 = new Comment(1L, "text1", LocalDateTime.now(), null, user);
        Comment c2 = new Comment(2L, "text2", LocalDateTime.now(), null, user);
        List<Comment> list = List.of(c1, c2);
        when(commentRepository.findAll()).thenReturn(list);
        CommentResponseList expected = new CommentResponseList(CommentMapper.toDtoList(list));
        CommentResponseList actual   = commentQueryService.getAllComments();
        assertEquals(expected, actual);
    }

    @Test
    void getCommentById() throws CommentNotFoundException {
        User user = new User(); user.setId(1L);
        Comment c = new Comment(1L, "hello", LocalDateTime.now(), null, user);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(c));
        CommentResponse expected = CommentMapper.toDto(c);
        CommentResponse actual   = commentQueryService.getCommentById(1L);
        assertEquals(expected, actual);
    }
}