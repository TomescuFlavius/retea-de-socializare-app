package app.photos.repository;

import app.photos.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> getPhotosByUser_Id(@Param("userId") Long userId);
    Optional<Photo> getPhotoByImgUrl(@Param("imgUrl") String imgUrl);
}
