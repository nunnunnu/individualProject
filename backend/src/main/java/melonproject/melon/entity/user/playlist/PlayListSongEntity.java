package melonproject.melon.entity.user.playlist;

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
import melonproject.melon.entity.artist.song.SongInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="playlist_song")
public class PlayListSongEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ps_seq") private Long psSeq; 
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="ps_playi_seq") private PlayListInfoEntity play; 
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="ps_si_seq") private SongInfoEntity song; 
    @Column(name="ps_order") private Integer psOrder; 
}