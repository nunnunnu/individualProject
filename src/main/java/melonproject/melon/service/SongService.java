package melonproject.melon.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SongCreatorEntity;
import melonproject.melon.entity.artist.song.SongFileEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.artist.song.SoundQuality;
import melonproject.melon.entity.info.GenreInfoEntity;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.artist.song.SongCreatorRepository;
import melonproject.melon.repository.artist.song.SongFileRepository;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.info.GenreInfoRepository;
import melonproject.melon.vo.song.SongAddVO;
import melonproject.melon.vo.song.SongCreatorAddVO;

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
}
