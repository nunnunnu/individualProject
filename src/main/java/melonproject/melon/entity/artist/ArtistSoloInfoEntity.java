package melonproject.melon.entity.artist;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
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
@Table(name="artist_solo_info")
@DiscriminatorValue("SOLO")
public class ArtistSoloInfoEntity extends ArtistInfoEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="art_seq") private Long artSeq;
    @Column(name="asi_birth") private Long asiBirth;
}