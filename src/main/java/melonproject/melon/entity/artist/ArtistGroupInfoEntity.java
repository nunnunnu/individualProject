package melonproject.melon.entity.artist;

import java.time.LocalDate;

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
@Table(name="artist_group_info")
@DiscriminatorValue("GROUP")
public class ArtistGroupInfoEntity extends ArtistInfoEntity{
    @Column(name="agi_split") private LocalDate agiSplit;
}