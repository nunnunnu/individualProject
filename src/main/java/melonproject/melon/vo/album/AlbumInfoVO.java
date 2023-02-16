package melonproject.melon.vo.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumInfoVO {
    private Long seq;
    private String name;
    private String uri;

    public AlbumInfoVO(AlbumInfoEntity entity){
        this.seq = entity.getAlbumSeq();
        this.name = entity.getAlbumName();
        this.uri = entity.getAlbumUri();
    }
}
