package melonproject.melon.vo.album;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.album.AlbumType;
import melonproject.melon.entity.artist.song.SongInfoEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistAlbumVO {
    private Long seq;
    private LocalDate regDt;
    private Long artistSeq;
    private String artistName;
    private AlbumType type;
    private String uri;
    private String name;
    private Integer songCount;
    private Long songSeq;
    private String songName;

    public ArtistAlbumVO(AlbumInfoEntity entity, SongInfoEntity song){
        this.seq = entity.getAlbumSeq();
        this.regDt = entity.getAlbumRegDt();
        this.artistSeq = entity.getArtist().getArtSeq();
        this.artistName = entity.getArtist().getArtName();
        this.type = entity.getAlbumType();
        this.uri = entity.getAlbumUri();
        this.name = entity.getAlbumName();
        this.songCount = entity.getSongList().size();
        if(song!=null){
            this.songName = song.getSiName();
            this.songSeq = song.getSiSeq();
        }
    }

}
