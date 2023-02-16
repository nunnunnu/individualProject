package melonproject.melon.entity.user;

import java.lang.reflect.Member;

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
@Table(name="song_likes")
public class SongLikesEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sl_seq") private Long slSeq;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="sl_si_seq") private SongInfoEntity song;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="sl_mi_seq") private MemberInfoEntity member;
}		