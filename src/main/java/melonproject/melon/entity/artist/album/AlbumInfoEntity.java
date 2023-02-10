package melonproject.melon.entity.artist.album;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
import melonproject.melon.entity.info.PublisherInfoEntity;
import melonproject.melon.entity.info.GenreInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="album_grade")
public class AlbumInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="album_seq") private Long albumSeq;
    @Column(name="album_reg_dt") private LocalDate albumRegDt;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="album_gi_seq") private GenreInfoEntity genre;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="album_pi_seq") private PublisherInfoEntity publisher;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="album_ai_seq") private AgencyInfoEntity agency;
    @Column(name="album_type") private AlbumType albumType;
    @Column(name="album_cover") private String albumCover;
    @Column(name="album_uri") private String albumUri;
}