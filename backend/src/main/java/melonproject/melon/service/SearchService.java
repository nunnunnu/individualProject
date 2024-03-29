package melonproject.melon.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.artist.song.SoundQuality;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.reader.MemberReader;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.artist.song.SongFileRepository;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.repository.user.SongLikesRepository;
import melonproject.melon.vo.album.AlbumInfoVO;
import melonproject.melon.vo.artist.ArtistChannelVO;
import melonproject.melon.vo.artist.ArtistListVO;
import melonproject.melon.vo.song.ArtistSongVO;
import melonproject.melon.vo.song.SongInfoVO;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SongInfoRepository songRepo;
    private final ArtistInfoRepository artistRepo;
    private final AlbumInfoRepository albumRepo;
    private final SongFileRepository sfRepo;
    private final SongLikesRepository slRepo;
    private final MemberInfoRepository mRepo;
    private final MemberReader memberReader;

    public Map<String, Object> searchTotal(String key, UserDetails userDetails, String type){
        Map<String, Object> map = new LinkedHashMap<>();
        if(type.equals("login") && userDetails==null){
            map.put("status", false);
            map.put("message", "만료된 토큰입니다.");
            map.put("code", HttpStatus.FORBIDDEN);
            return map;
        }
        List<SongInfoEntity> songNames = songRepo.findTop10BySiNameContains(key);
        List<SongInfoEntity> songLyrics = songRepo.findTop10BySiLyricsContains(key);
        List<AlbumInfoEntity> albums = albumRepo.findTop10ByAlbumNameContains(key);
        List<ArtistInfoEntity> artist = artistRepo.findTop10ByArtNameContains(key);

        List<SongInfoVO> songNameVO = new ArrayList<>();
        if(userDetails!=null){
            MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());
            for(SongInfoEntity s : songNames){
                songNameVO.add(new SongInfoVO(
                    s, 
                    sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3)!=null?sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3).getSfUri():null,
					slRepo.countBySongAndMember(s, member) >= 1
                    ));
            }
            
        }else{
            for(SongInfoEntity s : songNames){
                songNameVO.add(new SongInfoVO(
                    s, 
                    sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3)!=null?sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3).getSfUri():null
                    ));
            }
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
        map.put("status", false);
        map.put("code", HttpStatus.OK);
        return map;
    }

    public Map<String, Object> searchSongName(String keyword, Pageable page, UserDetails userDetails, String type){
        Map<String, Object> map = new LinkedHashMap<>();
        if(type.equals("login") && userDetails==null){
            map.put("status", false);
            map.put("message", "만료된 토큰입니다.");
            map.put("code", HttpStatus.FORBIDDEN);
            return map;
        }
        Page<SongInfoEntity> songs = songRepo.findBySiNameContains(keyword, page);
        if(userDetails!=null){
            MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());
            if(member==null){
                map.put("status", false);
                map.put("message", "정상적인 접근이 아닙니다.");
                map.put("code", HttpStatus.BAD_REQUEST);
                return map;
            }
            Page<SongInfoVO> songNameVO = songs.map(
                s->new SongInfoVO(s,
                sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3)!=null?sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3).getSfUri():null,
					slRepo.countBySongAndMember(s, member) >= 1
                ));
                map.put("data", songNameVO);
        }else{
            Page<SongInfoVO> songNameVO = songs.map(
                s->new SongInfoVO(s,
                sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3)!=null?sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3).getSfUri():null));
                map.put("data", songNameVO);
        }

            map.put("status", true);
            map.put("message", "조회성공");
            map.put("code", HttpStatus.OK);
        return map;
    }

    public Map<String, Object> artistNameSearch(String keyword, Pageable page){
        Map<String, Object> map = new LinkedHashMap<>();
        Page<ArtistInfoEntity> entity = artistRepo.findByArtNameContains(keyword, page);
        Page<ArtistChannelVO> artisNameVO = entity.map(
			ArtistChannelVO::new);
            
        map.put("status", true);
        map.put("message", "조회성공");
        map.put("code", HttpStatus.OK);
        map.put("data", artisNameVO);

        return map;
    }

    public Map<String, Object> songLyricsSearch(String keyword, Pageable page){
        Map<String, Object> map = new LinkedHashMap<>();

        Page<SongInfoEntity> songs = songRepo.findBySiLyricsContains(keyword, page);
        Page<ArtistSongVO> songLyricsVO = songs.map(
            s-> new ArtistSongVO(s, sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3)!=null?sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3).getSfUri():null));

        map.put("status", true);
        map.put("message", "조회성공");
        map.put("code", HttpStatus.OK);
        map.put("data", songLyricsVO);
        return map;
    }

    public Map<String, Object> albumSearch(String keyword, Pageable page){
        Map<String, Object> map = new LinkedHashMap<>();

        Page<AlbumInfoEntity> albums = albumRepo.findByAlbumNameContains(keyword, page);
        Page<AlbumInfoVO> albumVO = albums.map(
			AlbumInfoVO::new);

        map.put("status", true);
        map.put("message", "조회성공");
        map.put("code", HttpStatus.OK);
        map.put("data", albumVO);
        return map;
    }
}
