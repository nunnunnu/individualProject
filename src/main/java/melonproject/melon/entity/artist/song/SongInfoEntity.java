package melonproject.melon.entity.artist.song;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.info.GenreInfoEntity;
import melonproject.melon.entity.user.SongLikesEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="song_info")
@DynamicInsert
@Builder
public class SongInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="si_seq") private Long         siSeq;
    @Column(name="si_name") private String        siName;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="si_album_seq") private AlbumInfoEntity album;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="si_gi_seq") private GenreInfoEntity genre;
    // @ColumnDefault("false")
    @Column(name="si_title") private Boolean       siTitle;
    // @ColumnDefault("1")
    @Column(name="si_order") private Integer       siOrder;
    @Column(name="si_lyrics") private String      siLyrics;
    // @ColumnDefault("")
    @Column(name="si_reg_dt") private LocalDate      siRegDt;
    @Column(name="si_movie") private String siMovie;
    // @ManyToOne(fetch=FetchType.LAZY) @JsonIgnore @JoinColumn(name="si_art_seq") private ArtistInfoEntity artist;

    @OneToMany(mappedBy="song",fetch = FetchType.LAZY)
    private List<SongLikesEntity> likes = new ArrayList<>();
    @OneToMany(mappedBy="song", fetch = FetchType.LAZY)
    private List<SongFileEntity> files = new ArrayList<>();
    @OneToMany(mappedBy="song", fetch = FetchType.LAZY)
    private List<SongArtistConnection> artists = new ArrayList<>();
}
