package melonproject.melon.entity.user.playlist;

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
import melonproject.melon.entity.share.BaseTimeEntity;
import melonproject.melon.entity.user.MemberInfoEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="playlist_info")
public class PlayListInfoEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="playi_seq") private Long playiSeq;
    @Column(name="playi_title") private String playiTitle;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="playi_mi_seq") private MemberInfoEntity member;
    // @Column(name="playi_reg_dt") private LocalDateTime playiRegDt;
    // @Column(name="playi_edit_dt") private LocalDateTime playiEditDt;
}