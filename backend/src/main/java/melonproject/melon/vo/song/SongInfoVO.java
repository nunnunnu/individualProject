package melonproject.melon.vo.song;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.song.SongArtistConnection;
import melonproject.melon.entity.artist.song.SongFileEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.user.playlist.PlayListSongEntity;
import melonproject.melon.vo.album.AlbumInfoVO;
import melonproject.melon.vo.artist.ArtistInfoVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongInfoVO {
    private Long seq;
    private Integer order;
    private Boolean title;
    private String name;
    private List<ArtistInfoVO> artists = new ArrayList<>();
    private Integer likes; 
    private String movie;
    private List<SongFileVO> files = new ArrayList<>();
    private Boolean isliked;
    // private String albumUri;
    private String lyrics;
    private AlbumInfoVO album;
    private Integer playListOrder;

    public SongInfoVO(SongInfoEntity entity){
        this.seq = entity.getSiSeq();
        this.order = entity.getSiOrder();
        this.title = entity.getSiTitle();
        this.name = entity.getSiName();
        for(SongArtistConnection sac : entity.getArtists()){
            this.artists.add(new ArtistInfoVO(sac.getArtist()));
        }
        this.likes = entity.getLikes().size();
        this.movie = entity.getSiMovie();
        for(SongFileEntity file : entity.getFiles()){
            this.files.add(new SongFileVO(file));
        }
        // this.albumUri = entity.getAlbum().getAlbumUri();
        this.album = new AlbumInfoVO(entity.getAlbum());

    }
    public SongInfoVO(SongInfoEntity entity, Boolean liked){
        this.seq = entity.getSiSeq();
        this.order = entity.getSiOrder();
        this.title = entity.getSiTitle();
        this.name = entity.getSiName();
        for(SongArtistConnection sac : entity.getArtists()){
            this.artists.add(new ArtistInfoVO(sac.getArtist()));
        }
        this.likes = entity.getLikes().size();
        this.movie = entity.getSiMovie();
        for(SongFileEntity file : entity.getFiles()){
            this.files.add(new SongFileVO(file));
        }
        this.isliked=liked;
        // this.albumUri = entity.getAlbum().getAlbumUri();
        this.album = new AlbumInfoVO(entity.getAlbum());
    }
    public SongInfoVO(SongInfoEntity song, String uri, Boolean liked){
        this.seq = song.getSiSeq();
        this.name = song.getSiName();
        // this.albumUri = song.getAlbum().getAlbumUri();
        this.title = song.getSiTitle();
        for(SongArtistConnection a : song.getArtists()){
            artists.add(new ArtistInfoVO(a.getArtist()));
        }
        for(SongFileEntity file : song.getFiles()){
            this.files.add(new SongFileVO(file));
        }
        this.album = new AlbumInfoVO(song.getAlbum());
        this.likes = song.getLikes().size();
        this.movie = song.getSiMovie();
        this.lyrics = song.getSiLyrics();
        this.isliked = liked;
        this.album = new AlbumInfoVO(song.getAlbum());
    }
    public SongInfoVO(SongInfoEntity entity, String uri){
        this.seq = entity.getSiSeq();
        this.order = entity.getSiOrder();
        this.title = entity.getSiTitle();
        this.name = entity.getSiName();
        for(SongArtistConnection sac : entity.getArtists()){
            this.artists.add(new ArtistInfoVO(sac.getArtist()));
        }
        this.likes = entity.getLikes().size();
        this.movie = entity.getSiMovie();
        for(SongFileEntity file : entity.getFiles()){
            this.files.add(new SongFileVO(file));
        }
        // this.isliked=liked;
        // this.albumUri = entity.getAlbum().getAlbumUri();
        this.album = new AlbumInfoVO(entity.getAlbum());
    }

    public SongInfoVO(PlayListSongEntity p){
        SongInfoEntity entity = p.getSong();
        this.seq = entity.getSiSeq();
        this.order = entity.getSiOrder();
        this.title = entity.getSiTitle();
        this.name = entity.getSiName();
        for(SongArtistConnection sac : entity.getArtists()){
            this.artists.add(new ArtistInfoVO(sac.getArtist()));
        }
        this.likes = entity.getLikes().size();
        this.movie = entity.getSiMovie();
        for(SongFileEntity file : entity.getFiles()){
            this.files.add(new SongFileVO(file));
        }
        // this.isliked=liked;
        // this.albumUri = entity.getAlbum().getAlbumUri();
        this.album = new AlbumInfoVO(entity.getAlbum());
        this.playListOrder = p.getPsOrder();
    }
}
