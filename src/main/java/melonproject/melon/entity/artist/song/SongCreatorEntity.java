package melonproject.melon.entity.artist.song;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="song_creator")
public class SongCreatorEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sc_seq") private Long     scSeq;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="sc_si_seq") private SongInfoEntity song;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="sc_art_seq") private ArtistInfoEntity artist;
    @Column(name="sc_type") private SongWirter scType;
}