package melonproject.melon.vo.song.Creator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.song.SongCreatorEntity;
import melonproject.melon.entity.artist.song.SongWirter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatorInfoVO {
    private Long seq;
    private String name;
    private SongWirter type;
    private String uri;

    public CreatorInfoVO(SongCreatorEntity entity){
        this.seq = entity.getScSeq();
        this.name = entity.getArtist().getArtName();
        this.type = entity.getScType();
        this.uri = entity.getArtist().getArtUri();
    }
}
