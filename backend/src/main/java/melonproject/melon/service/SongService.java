package melonproject.melon.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SongArtistConnection;
import melonproject.melon.entity.artist.song.SongCreatorEntity;
import melonproject.melon.entity.artist.song.SongFileEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.artist.song.SoundQuality;
import melonproject.melon.entity.info.GenreInfoEntity;
import melonproject.melon.entity.user.HistoryPlayEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.artist.song.SongArtistConnectionRepository;
import melonproject.melon.repository.artist.song.SongCreatorRepository;
import melonproject.melon.repository.artist.song.SongFileRepository;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.info.GenreInfoRepository;
import melonproject.melon.repository.user.HistoryPlayRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.vo.song.ArtistSongVO;
import melonproject.melon.vo.song.SongAddVO;
import melonproject.melon.vo.song.SongDetailVO;
import melonproject.melon.vo.song.Creator.SongCreatorAddVO;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongInfoRepository songRepo;
    private final AlbumInfoRepository albumRepo;
    private final GenreInfoRepository giRepo;
    private final ArtistInfoRepository artRepo;
    private final SongCreatorRepository scRepo;
    private final FileService fService;
    private final SongFileRepository sfRepo;
    private final MemberInfoRepository mRepo;
    private final HistoryPlayRepository hpRepo;
    private final SongArtistConnectionRepository sacRepo;

    public Map<String, Object> songAdd(SongAddVO data){
        System.out.println(data);
        Map<String, Object> map = new LinkedHashMap<>();
        if(data.getAlbum()==null || data.getGenre()==null || data.getName()==null ){
            map.put("status", false);
            map.put("message", "필수 값을 모두 입력해주세요");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        AlbumInfoEntity album = albumRepo.findById(data.getAlbum()).orElse(null);
        if(album==null){
            map.put("status", false);
            map.put("message", "앨범 번호가 잘못됐습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        GenreInfoEntity genre = giRepo.findById(data.getAlbum()).orElse(null);
        if(genre==null){
            map.put("status", false);
            map.put("message", "장르 번호가 잘못됐습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        // SongInfoEntity song = new SongInfoEntity(null, data.getName(), album, genre, data.getTitle(), data.getOrder(), data.getLyrics(), data.getRegDt());
        SongInfoEntity song = SongInfoEntity.builder()
                                .siName(data.getName())
                                .album(album)
                                .genre(genre)
                                .siTitle(data.getTitle())
                                .siOrder(data.getOrder())
                                .siLyrics(data.getLyrics())
                                .siRegDt(data.getRegDt())
                                .build();
        
        
        songRepo.save(song);
        map.put("status", true);
        map.put("message", "곡 정보를 등록했습니다.");
        map.put("code", HttpStatus.OK);

        return map;
    }
    public Map<String, Object> songCreatorConnection(SongCreatorAddVO data){
        Map<String, Object> map = new LinkedHashMap<>();
        if(data.getSong()==null || data.getArt()==null || data.getType()==null){
            map.put("status", false);
            map.put("message", "필수 값을 모두 입력해주세요");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        SongInfoEntity song= songRepo.findById(data.getSong()).orElse(null);
        if(song==null){
            map.put("status", false);
            map.put("message", "음원 번호 오류입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        ArtistInfoEntity art = artRepo.findById(data.getArt()).orElse(null);
        if(art == null){
            map.put("status", false);
            map.put("message", "아티스트 번호가 오류입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        SongCreatorEntity sc = new SongCreatorEntity(null, song, art, data.getType());
        scRepo.save(sc);
        map.put("status", true);
        map.put("message", data.getType()+" 정보를 저장했습니다");
        map.put("code", HttpStatus.OK);
        
        return map;
    }

    public Map<String, Object> songFileAdd(Long seq, SoundQuality sound, MultipartFile file){
        Map<String, Object> map = new LinkedHashMap<>();

        if(seq == null || sound == null){
            map.put("status", false);
            map.put("message", "필수 값을 모두 입력해주세요");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }

        SongInfoEntity song = songRepo.findById(seq).orElse(null);
        if(song==null){
            map.put("status", false);
            map.put("message", "음원 번호 오류입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        SongFileEntity entity = fService.saveSongFile(file);
        entity.fileSaveSetting(song, sound);
        sfRepo.save(entity);

        map.put("status", true);
        map.put("message", "음원 파일을 저장했습니다");
        map.put("code", HttpStatus.OK);

        return map;
    }
    public void listenMusicList(Long memberSeq, Long songSeq){
        MemberInfoEntity member = mRepo.findById(memberSeq).orElse(null);
        if(member==null){
            // map.put("status", false);
            // map.put("message", "회원번호 오류");
            // map.put("code", HttpStatus.BAD_GATEWAY);
            return ;
        }
        SongInfoEntity song = songRepo.findById(songSeq).orElse(null);
        if(song==null){
            // map.put("status", false);
            // map.put("message", "곡번호 오류");
            // map.put("code", HttpStatus.BAD_GATEWAY);
            return ;
        }
        HistoryPlayEntity entity = new HistoryPlayEntity(null, song, member, LocalDateTime.now(), null);
        hpRepo.save(entity);
        // map.put("status", true);
        // map.put("message", "저장성공");
        // map.put("code", HttpStatus.OK);
        return ;
    }
    public Map<String, Object> songDetailShow(Long seq){
        Map<String, Object> map = new LinkedHashMap<>();
        SongInfoEntity song = songRepo.findById(seq).orElse(null);
        if(song==null){
            map.put("status", false);
            map.put("message", "곡번호 오류");
            map.put("code", HttpStatus.BAD_GATEWAY);
            return map;
        }
        SongDetailVO songVO = new SongDetailVO(song);
        map.put("status", true);
        map.put("message", "성공");
        map.put("code", HttpStatus.OK);
        map.put("data", songVO);
        return map;
    }
    public Map<String, Object> artistSongParticipation(Long seq, Pageable page){
        Map<String, Object> map = new LinkedHashMap<>();
        ArtistInfoEntity artist = artRepo.findById(seq).orElse(null);
        if(artist==null){
            map.put("status", false);
            map.put("message", "아티스트 번호 오류입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        Page<SongArtistConnection> songs = sacRepo.findByArtist(artist, page);
        if(songs.getContent().size()==0){
            map.put("status", false);
            map.put("message", "조회할 값이 없음");
            map.put("code", HttpStatus.NO_CONTENT);
            return map;
        }
        // List<ArtistSongVO> songVO = new ArrayList<>();
        Page<ArtistSongVO> result = songs.map(
            s->new ArtistSongVO(s.getSong(),
            sfRepo.findBySongAndSfQuality(s.getSong(), SoundQuality.MP3)!=null?sfRepo.findBySongAndSfQuality(s.getSong(), SoundQuality.MP3).getSfUri():null));
        map.put("status", true);
        map.put("message", "조회성공.");
        map.put("code", HttpStatus.OK);
        map.put("data", result);
        return map;
    }

    public Map<String, Object> newSongList(Pageable page){
        Map<String, Object> map = new LinkedHashMap<>();
        LocalDate now = LocalDate.now().minusMonths(1);
        Page<SongInfoEntity> entity = songRepo.findBySiRegDtLessThanEqual(now, page);
        Page<ArtistSongVO> result = entity.map(
            s->new ArtistSongVO(s,
            sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3)!=null?sfRepo.findBySongAndSfQuality(s, SoundQuality.MP3).getSfUri():null));

        map.put("status", true);
        map.put("message", "조회성공.");
        map.put("code", HttpStatus.OK);
        map.put("data", result);
        return map;
    }
}
