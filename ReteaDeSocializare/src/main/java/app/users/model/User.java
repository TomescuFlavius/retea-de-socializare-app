package app.users.model;

import app.photos.model.Photo;
import app.security.Permissions;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(name= "users")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;

    @PastOrPresent(message = "Data nu poate fi in viitor")
    private LocalDate createdAt;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "permission_groups",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "permission_group", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Permissions> permissionGroups = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissionGroups.stream()
                .map(Permissions::getPermission)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Photo> photos=new HashSet<>();

    public User(Long id, String username, String password, String email, LocalDate createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setUser(this);
    }

    public void removePhoto(Photo photo) {
        photos.remove(photo);
        photo.setUser(null);
    }
}
