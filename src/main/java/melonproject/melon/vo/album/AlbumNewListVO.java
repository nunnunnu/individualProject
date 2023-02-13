package melonproject.melon.vo.album;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.album.AlbumType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumNewListVO {
    private Long seq;
    private LocalDate regDt;
    private Long artistSeq;
    private String artistName;
    private AlbumType type;
    private String uri;
    private String name;
    private Integer songCount;

    public AlbumNewListVO(AlbumInfoEntity entity){
        this.seq = entity.getAlbumSeq();
        this.regDt = entity.getAlbumRegDt();
        this.artistSeq = entity.getArtist().getArtSeq();
        this.artistName = entity.getArtist().getArtName();
        this.type = entity.getAlbumType();
        this.uri = entity.getAlbumUri();
        this.name = entity.getAlbumName();
        this.songCount = entity.getSongList().size();
    }

}
