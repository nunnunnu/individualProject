package melonproject.melon.entity.user;

import java.time.LocalDateTime;

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
import melonproject.melon.entity.artist.ArtistInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="artist_fan")
public class ArtistFanEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="af_seq") private Long afSeq;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="af_art_seq") private ArtistInfoEntity artist;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="af_mi_seq") private MemberInfoEntity member;
    @Column(name="af_reg_dt") private LocalDateTime afRegDt;

}
