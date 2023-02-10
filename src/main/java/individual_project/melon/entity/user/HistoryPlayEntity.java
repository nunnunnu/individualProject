package individual_project.melon.entity.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="history_play")
public class HistoryPlayEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hp_seq") private Long hpSeq;
    @Column(name="hp_si_seq") private Long hpSiSeq;
    @Column(name="hp_mi_seq") private Long hpMiSeq;
    @Column(name="hp_start") private LocalDateTime hpStart;
    @Column(name="hp_end") private LocalDateTime hpEnd;
}