package individual_project.melon.entity.artist.album;

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
@Table(name="album_comment")
public class AlbumCommentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="albumc_seq") private Long albumcSeq;
    @Column(name="albumc_mi_seq") private Long albumcMiSeq;
    @Column(name="albumc_album_seq") private Long albumcAlbumSeq;
    @Column(name="albumc_commnet") private String albumcCommnet;
    @Column(name="albumc_reg_dt") private LocalDateTime albumcRegDt;
    @Column(name="albumc_edit_dt") private LocalDateTime albumcEditDt;
    @Column(name="albumc_file") private String albumcFile;
    @Column(name="albumc_uri") private String albumcUri;

}
