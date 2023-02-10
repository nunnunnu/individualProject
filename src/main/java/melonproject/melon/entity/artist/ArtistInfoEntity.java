package melonproject.melon.entity.artist;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import melonproject.melon.entity.info.AgencyInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="artist_info")
@DiscriminatorColumn//기본값이 DTYPE
public class ArtistInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="art_seq") private Long artSeq;
    @Column(name="art_name") private String artName;
    @Column(name="art_debut") private LocalDate artDebut;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="art_ai_seq") private AgencyInfoEntity agency;
    @Column(name="art_country") private String artCountry;
    @Column(name="art_file") private String artFile;
    @Column(name="art_uri") private String artUri;
    @Column(name="art_dtype") private String artDtype;
}