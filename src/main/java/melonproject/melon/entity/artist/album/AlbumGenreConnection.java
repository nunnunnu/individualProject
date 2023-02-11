package melonproject.melon.entity.artist.album;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import melonproject.melon.entity.info.GenreInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="album_genre_connection")
public class AlbumGenreConnection {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="agc_seq") private Long agcSeq;
    @ManyToOne @JsonIgnore @JoinColumn(name="agc_gi_seq") private GenreInfoEntity genre;
    @ManyToOne @JsonIgnore @JoinColumn(name="agc_album_seq") private AlbumInfoEntity album;
}