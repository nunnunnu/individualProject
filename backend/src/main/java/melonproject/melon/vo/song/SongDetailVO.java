package melonproject.melon.vo.song;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.song.SongArtistConnection;
import melonproject.melon.entity.artist.song.SongCreatorEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.vo.album.AlbumInfoVO;
import melonproject.melon.vo.artist.ArtistInfoVO;
import melonproject.melon.vo.song.Creator.CreatorInfoVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDetailVO {
    private Long seq;
    private String name;
    private LocalDate regDt;
    private List<ArtistInfoVO> artists = new ArrayList<>();
    private String genre;
    private Integer likes;
    private AlbumInfoVO album;
    private String lyrics;
    private List<CreatorInfoVO> creates = new ArrayList<>();
    private String movie;
    private Boolean isLiked;

    public SongDetailVO(SongInfoEntity entity){
        this.seq = entity.getSiSeq();
        this.name = entity.getSiName();
        this.regDt = entity.getSiRegDt();
        this.genre = entity.getGenre().getGiName();
        this.likes = entity.getLikes().size();
        this.lyrics = entity.getSiLyrics().replace("\n", "<br />");
        artistsAdd(entity.getArtists());
        createsAdd(entity.getCreators());
        this.album = new AlbumInfoVO(entity.getAlbum());
        this.movie=entity.getSiMovie();
    }
    public SongDetailVO(SongInfoEntity entity, Boolean liked){
        this.seq = entity.getSiSeq();
        this.name = entity.getSiName();
        this.regDt = entity.getSiRegDt();
        this.genre = entity.getGenre().getGiName();
        this.likes = entity.getLikes().size();
        this.lyrics = entity.getSiLyrics().replace("\n", "<br />");
        artistsAdd(entity.getArtists());
        createsAdd(entity.getCreators());
        this.album = new AlbumInfoVO(entity.getAlbum());
        this.movie=entity.getSiMovie();
        this.isLiked = liked;
    }
    public void artistsAdd(List<SongArtistConnection> art){
        for(SongArtistConnection a : art){
            artists.add(new ArtistInfoVO(a.getArtist()));
        }
    }
    public void createsAdd(List<SongCreatorEntity> creator){
        for(SongCreatorEntity s : creator){
            creates.add(new CreatorInfoVO(s));
        }
    }

}
