package melonproject.melon.vo.song;

import lombok.Data;
import melonproject.melon.entity.artist.song.SongWirter;
@Data
public class SongCreatorAddVO {
    private Long song;
    private Long art;
    private SongWirter type;
}
