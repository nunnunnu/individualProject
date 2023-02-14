package melonproject.melon.vo.album;

import java.time.LocalDate;
import java.util.List;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumGenreConnection;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.album.AlbumType;
import melonproject.melon.vo.artist.ArtistInfoVO;
import melonproject.melon.vo.song.SongInfoVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDetailVO {
    private AlbumType type;
    private String name;
    private String genreList;
    private String pubName;
    private String agencyName;
    private Integer songCount;
    private Integer comment;
    private LocalDate regDt;
    private ArtistInfoVO artist;
    private List<SongInfoVO> song;
    private String uri;
    private String explan;

    public AlbumDetailVO(AlbumInfoEntity album){
        this.type = album.getAlbumType();
        this.name = album.getAlbumName();
        for(AlbumGenreConnection g : album.getGenreList()){
            if(StringUtils.hasText(genreList)){
                this.genreList += ", ";
            }
            this.genreList += g.getGenre().getGiName();
        }
        this.pubName = album.getPublisher().getPiName();
        this.agencyName = album.getAgency().getAiName();
        this.songCount = album.getSongList().size();
        this.comment = album.getCommentList().size();
        this.regDt = album.getAlbumRegDt();
        this.uri = album.getAlbumUri();
        this.explan = album.getAlbumExplan();
    }

    public void artistVOsetting(ArtistInfoVO artist){
        this.artist = artist;
    }

    public void addSongList(List<SongInfoVO> songs){
        this.song.addAll(songs);
    }
    

}
