package melonproject.melon.entity.artist;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@SuperBuilder
@Entity
@Table(name="artist_solo_info")
@DiscriminatorValue("SOLO")
public class ArtistSoloInfoEntity extends ArtistInfoEntity{
    @Column(name="asi_birth") private LocalDate asiBirth;

    public void addSetting(AgencyInfoEntity agency, LocalDate birth, String country, LocalDate debut, String name) {
        this.asiBirth = birth;
        this.setAgency(agency);
        this.setArtCountry(country);
        this.setArtDebut(debut);
        this.setArtFile(name);
        this.setArtName(name);
        this.setArtUri(name);
    }
}