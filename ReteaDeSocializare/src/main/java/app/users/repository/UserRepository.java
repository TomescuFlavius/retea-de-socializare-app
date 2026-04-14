package app.users.repository;
import app.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserById(@Param ("id")long id);
    List<User> findUsersByUsername(@Param("username") String  username);
    List<User> findUsersByEmail(@Param("email") String email);
    List<User> findAll();
    boolean existsUserByUsername(@Param("username") String username);
    Optional<User> findUserByEmail(@Param ("email")String email);
}
