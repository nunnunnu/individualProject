package melonproject.melon.entity.user;

import java.time.LocalDateTime;

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
@Table(name="history_play")
public class HistoryPlayEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hp_seq") private Long hpSeq;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="hp_si_seq") private SongInfoEntity song;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="hp_mi_seq") private MemberInfoEntity member;
    @Column(name="hp_start") private LocalDateTime hpStart;
    @Column(name="hp_end") private LocalDateTime hpEnd;
}