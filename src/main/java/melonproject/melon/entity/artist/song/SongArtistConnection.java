package melonproject.melon.entity.artist.song;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import melonproject.melon.entity.artist.ArtistInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="song_artist_connection")
public class SongArtistConnection {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sac_seq") private Long sacSeq;
    @ManyToOne(fetch=FetchType.LAZY) @JsonIgnore @JoinColumn(name="sac_art_seq") private ArtistInfoEntity artist;
    @ManyToOne(fetch=FetchType.LAZY) @JsonIgnore @JoinColumn(name="sac_si_seq") private SongInfoEntity song;
}
