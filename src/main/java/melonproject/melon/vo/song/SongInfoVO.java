package melonproject.melon.vo.song;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.song.SongArtistConnection;
import melonproject.melon.entity.artist.song.SongFileEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.vo.artist.ArtistInfoVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongInfoVO {
    private Integer order;
    private Boolean title;
    private String name;
    private List<ArtistInfoVO> artists = new ArrayList<>();
    private Integer likes; 
    private String movie;
    private List<SongFileVO> files = new ArrayList<>();;

    public SongInfoVO(SongInfoEntity entity){
        this.order = entity.getSiOrder();
        this.title = entity.getSiTitle();
        this.name = entity.getSiName();
        for(SongArtistConnection sac : entity.getArtists()){
            this.artists.add(new ArtistInfoVO(sac.getArtist()));
        }
        this.likes = entity.getLikes().size();
        this.movie = entity.getSiMovie();
        for(SongFileEntity file : entity.getFiles()){
            System.out.println("sss");
            this.files.add(new SongFileVO(file));
        }

    }
}
