package melonproject.melon.vo.song;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.song.SongFileEntity;
import melonproject.melon.entity.artist.song.SoundQuality;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongFileVO {
    private SoundQuality quality;
    private String uri;

    public SongFileVO(SongFileEntity entity){
        this.quality = entity.getSfQuality();
        this.uri = entity.getSfUri();
    }
}
