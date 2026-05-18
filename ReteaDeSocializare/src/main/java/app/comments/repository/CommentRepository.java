package app.comments.repository;

import app.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getCommentsByPhoto_Id(@Param("photoId") Long photoId);
    List<Comment> getCommentsByUser_Id(@Param("userId") Long userId);
}