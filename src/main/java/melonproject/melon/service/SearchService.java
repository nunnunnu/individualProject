package melonproject.melon.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.artist.song.SoundQuality;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.artist.song.SongFileRepository;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.vo.album.AlbumInfoVO;
import melonproject.melon.vo.artist.ArtistListVO;
import melonproject.melon.vo.song.ArtistSongVO;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SongInfoRepository songRepo;
    private final ArtistInfoRepository artistRepo;
    private final AlbumInfoRepository albumRepo;
    private final SongFileRepository sfRepo;

    public Map<String, Object> searchTotal(String key){
        Map<String, Object> map = new LinkedHashMap<>();
        List<SongInfoEntity> songNames = songRepo.findTop10BySiNameContains(key);
        List<SongInfoEntity> songLyrics = songRepo.findTop10BySiLyricsContains(key);
        List<AlbumInfoEntity> albums = albumRepo.findTop10ByAlbumNameContains(key);
        List<ArtistInfoEntity> artist = artistRepo.findTop10ByArtNameContains(key);

        List<ArtistSongVO> songNameVO = new ArrayList<>();
        for(SongInfoEntity s : songNames){
            songNameVO.add(new ArtistSongVO(s, sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3)!=null?sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3).getSfUri():null));
        }
        List<ArtistSongVO> songLyricsVO = new ArrayList<>();
        for(SongInfoEntity s : songLyrics){
            songLyricsVO.add(new ArtistSongVO(s, sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3)!=null?sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3).getSfUri():null));
        }
        List<AlbumInfoVO> albumsVO = new ArrayList<>();
        for(AlbumInfoEntity s : albums){
            albumsVO.add(new AlbumInfoVO(s));
        }
        List<ArtistListVO> artistVO = new ArrayList<>();
        for(ArtistInfoEntity s : artist){
            artistVO.add(new ArtistListVO(s));
        }

        map.put("songName", songNameVO);
        map.put("songLyrics", songLyricsVO);
        map.put("albums", albumsVO);
        map.put("artist", artistVO);
        return map;
    }
}
