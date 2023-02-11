package melonproject.melon.entity.artist.song;

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
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.info.GenreInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="song_info")
public class SongInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="si_seq") private Long         siSeq;
    @Column(name="si_name") private String        siName;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="si_album_seq") private AlbumInfoEntity album;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="si_gi_seq") private GenreInfoEntity genre;
    @Column(name="si_title") private String       siTitle;
    @Column(name="si_order") private Integer       siOrder;
    @Column(name="si_lyrics") private String      siLyrics;
    @Column(name="si_reg_dt") private LocalDate      siRegDt;
    @ManyToOne(fetch=FetchType.LAZY) @JsonIgnore @JoinColumn(name="si_art_seq") private ArtistInfoEntity artist;
}
