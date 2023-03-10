package melonproject.melon.vo.playlist;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.user.playlist.PlayListInfoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayListInfoVO {
    private Long seq;
    private String title;
    private Integer songcount;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime regDt;
    
    public PlayListInfoVO(PlayListInfoEntity playlist){
        this.seq = playlist.getPlayiSeq();
        this.title = playlist.getPlayiTitle();
        this.songcount = playlist.getSongs().size();
        this.regDt = playlist.getCreatedDate();
    }
}
