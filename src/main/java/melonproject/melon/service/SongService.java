package melonproject.melon.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.info.GenreInfoEntity;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.info.GenreInfoRepository;
import melonproject.melon.vo.song.SongAddVO;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongInfoRepository songRepo;
    private final AlbumInfoRepository albumRepo;
    private final GenreInfoRepository giRepo;

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
        SongInfoEntity song = new SongInfoEntity(null, data.getName(), album, genre, data.getTitle(), data.getOrder(), data.getLyrics(), data.getRegDt());
        
        songRepo.save(song);
        map.put("status", true);
        map.put("message", "곡 정보를 등록했습니다.");
        map.put("code", HttpStatus.OK);

        return map;
    }
}
