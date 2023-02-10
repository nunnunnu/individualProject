package individual_project.melon.entity.artist.album;

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
@Table(name="album_grade")
public class AlbumGradeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ag_seq") private Long agSeq;
    @Column(name="ag_album_seq") private Long agAlbumSeq;
    @Column(name="ag_grade") private Double agGrade;
    @Column(name="ag_mi_seq") private Long agMiSeq;
}
