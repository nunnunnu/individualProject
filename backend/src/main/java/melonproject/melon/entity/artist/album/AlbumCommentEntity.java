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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import melonproject.melon.entity.share.BaseTimeEntity;
import melonproject.melon.entity.user.MemberInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="album_comment")
public class AlbumCommentEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="albumc_seq") private Long albumcSeq;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="albumc_mi_seq") private MemberInfoEntity member;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="albumc_album_seq") private AlbumInfoEntity album;
    @Column(name="albumc_commnet") private String albumcCommnet;
    // @Column(name="albumc_reg_dt") private LocalDateTime albumcRegDt;
    // @Column(name="albumc_edit_dt") private LocalDateTime albumcEditDt;
    @Column(name="albumc_file") private String albumcFile;
    @Column(name="albumc_uri") private String albumcUri;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="albumc_albumc_seq") private AlbumCommentEntity parent;

    public void setting(MemberInfoEntity member, AlbumInfoEntity album, String comment, AlbumCommentEntity parent){
        this.member = member;
        this.album = album;
        this.albumcCommnet = comment;
        this.parent = parent;
    }

}
