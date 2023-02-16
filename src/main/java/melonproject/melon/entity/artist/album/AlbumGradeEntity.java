package melonproject.melon.entity.artist.album;

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
import melonproject.melon.entity.user.MemberInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="album_grade")
public class AlbumGradeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ag_seq") private Long agSeq;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="ag_album_seq") private AlbumInfoEntity album;
    @Column(name="ag_grade") private Double agGrade;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="ag_mi_seq") private MemberInfoEntity member;
}