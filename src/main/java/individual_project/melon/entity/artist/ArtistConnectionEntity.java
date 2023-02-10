package individual_project.melon.entity.artist;

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
@Table(name="artist_connection")
public class ArtistConnectionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ac_seq") private Long acSeq;
    @Column(name="ac_agi_seq") private Long acAgiSeq;
    @Column(name="ac_asi_seq") private Long acAsiSeq;
}
