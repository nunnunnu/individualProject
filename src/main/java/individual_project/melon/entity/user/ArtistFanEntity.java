package individual_project.melon.entity.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="artist_fan")
public class ArtistFanEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="af_seq") private Long afSeq;
    @Column(name="af_art_seq") private Long afArtSeq;
    @Column(name="af_mi_seq") private Long afMiSeq;
    @Column(name="af_reg_dt") private LocalDateTime afRegDt;

}
