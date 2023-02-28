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
import lombok.experimental.SuperBuilder;
import melonproject.melon.entity.info.AgencyInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="artist_group_info")
@SuperBuilder
@DiscriminatorValue("GROUP")
public class ArtistGroupInfoEntity extends ArtistInfoEntity{
    @Column(name="agi_split") private LocalDate agiSplit;

    public void addSetting(AgencyInfoEntity agency, String country, LocalDate debut, String name, LocalDate split) {
        this.agiSplit = split;
        this.setAgency(agency);
        this.setArtCountry(country);
        this.setArtDebut(debut);
        this.setArtName(name);
    }
}