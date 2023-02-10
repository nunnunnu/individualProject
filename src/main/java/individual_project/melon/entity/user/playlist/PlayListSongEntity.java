package individual_project.melon.entity.user.playlist;

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
public class PlayListSongEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ps_seq") private Long psSeq; 
    @Column(name="ps_playi_seq") private Long psPlayiSeq; 
    @Column(name="ps_si_seq") private Long psSiSeq; 
    @Column(name="ps_order") private Long psOrder; 
}