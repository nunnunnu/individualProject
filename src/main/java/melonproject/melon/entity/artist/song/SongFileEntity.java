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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="song_file")
@Builder
public class SongFileEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sf_seq") private Long sfSeq; 
    @Column(name="sf_file") private String sfFile; 
    @Column(name="sf_uri") private String sfUri; 
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="sf_si_seq") private SongInfoEntity sfSiSeq; 
    @Column(name="sf_quality") private String sfQuality; 
}