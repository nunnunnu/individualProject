package melonproject.melon.entity.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.song.SongInfoEntity;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "down_history")
public class DownHistoryEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dh_seq") private Long dhSeq;
    @ManyToOne @JoinColumn(name="dh_si_seq") private SongInfoEntity song;
    @ManyToOne @JoinColumn(name="dh_mi_seq") private MemberInfoEntity member;
    @Column(name="dh_date") private LocalDateTime dhDate;

}
			