package app.photos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name= "photos")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String imgUrl;
    @Id
    private Long userId;
    @PastOrPresent(message = "Data nu poate fi in viitor")
    private LocalDateTime createdAt;
}
