package melonproject.melon.vo.song;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.song.SongFileEntity;
import melonproject.melon.entity.artist.song.SoundQuality;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongFileInfoVO {
    private Long seq;
    private String file;
    private String uri;
    private SoundQuality quality;

    public SongFileInfoVO(SongFileEntity file){
        this.seq = file.getSfSeq();
        this.file = file.getSfFile();
        this.uri = file.getSfUri();
        this.quality = file.getSfQuality();
    }
}
