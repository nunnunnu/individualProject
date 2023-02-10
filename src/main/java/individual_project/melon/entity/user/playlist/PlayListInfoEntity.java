package individual_project.melon.entity.user.playlist;

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
@Table(name="artist_fan")
public class PlayListInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="playi_seq") private Long playiSeq;
    @Column(name="playi_title") private String playiTitle;
    @Column(name="playi_mi_seq") private Long playiMiSeq;
    @Column(name="playi_reg_dt") private LocalDateTime playiRegDt;
    @Column(name="playi_edit_dt") private LocalDateTime playiEditDt;
}