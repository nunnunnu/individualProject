package melonproject.melon.vo.album;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.vo.artist.ArtistInfoVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAlbum {
    private Long seq;
    private String name;
    private ArtistInfoVO artist;
    private LocalDate regDt;
    private String uri;

    public NewAlbum(AlbumInfoEntity album){
        this.seq = album.getAlbumSeq();
        this.name = album.getAlbumName();
        this.artist = new ArtistInfoVO(album.getArtist());
        this.regDt = album.getAlbumRegDt();
        this.uri = "http://15.164.21.92:8250/image/album/"+album.getAlbumUri();
    }
}
