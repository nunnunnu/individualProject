package melonproject.melon.vo.song;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.song.SongArtistConnection;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.vo.album.AlbumInfoVO;
import melonproject.melon.vo.artist.ArtistInfoVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistSongVO {
    private String name;
    private String uri;
    private Boolean title;
    private List<ArtistInfoVO> artist = new ArrayList<>();
    private AlbumInfoVO album;
    private Integer like;
    private String movie;

    public ArtistSongVO(SongInfoEntity song, String uri){
        this.name = song.getSiName();
        this.uri = uri;
        this.title = song.getSiTitle();
        for(SongArtistConnection a : song.getArtists()){
            artist.add(new ArtistInfoVO(a.getArtist()));
        }
        this.album = new AlbumInfoVO(song.getAlbum());
        this.like = song.getLikes().size();
        this.movie = song.getSiMovie();
    }

    
}
