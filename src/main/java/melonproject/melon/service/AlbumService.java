package melonproject.melon.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.album.AlbumGenreConnection;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.info.AgencyInfoEntity;
import melonproject.melon.entity.info.GenreInfoEntity;
import melonproject.melon.entity.info.PublisherInfoEntity;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.album.AlbumGenreConnectionRepository;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.info.AgencyInfoRepository;
import melonproject.melon.repository.info.GenreInfoRepository;
import melonproject.melon.repository.info.PublisherInfoRepository;
import melonproject.melon.vo.album.AlbumAddVO;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumInfoRepository albumRepo;
    private final PublisherInfoRepository pubRepo;
    private final AgencyInfoRepository agenRepo;
    private final ArtistInfoRepository artRepo;
    private final FileService fService;
    private final GenreInfoRepository genreRepo;
    private final AlbumGenreConnectionRepository agRepo;

    public Map<String, Object> addAlbum(AlbumAddVO data, MultipartFile file){
        Map<String, Object> map = new LinkedHashMap<>();
        if(data.getRegDt()==null || data.getAgency()==null || data.getName()==null || data.getPublisher()==null || data.getType()==null){
            map.put("status", false);
            map.put("message", "필수 값을 모두 입력해주세요");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        PublisherInfoEntity pub = pubRepo.findById(data.getPublisher()).orElse(null);
        System.out.println(pub);
        if(pub==null){
            map.put("status", false);
            map.put("message", "출판사번호가 일치하지 않습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        AgencyInfoEntity agen = agenRepo.findById(data.getAgency()).orElse(null);
        if(agen==null){
            map.put("status", false);
            map.put("message", "소속사번호가 일치하지 않습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        ArtistInfoEntity art = null;

        if(data.getArtist()!=null){
            art = artRepo.findById(data.getArtist()).orElse(null);
            if(art==null){
                map.put("status", false);
                map.put("message", "아티스트 번호가 일치하지 않습니다.");
                map.put("code", HttpStatus.BAD_REQUEST);
                return map;
            }
        }
        AlbumInfoEntity album = fService.saveAlbumFile(file);
        album.setting(data.getRegDt(), pub, agen, art, data.getType(), data.getName());

        albumRepo.save(album);
        map.put("status", true);
        map.put("message", "앨범 정보를 등록했습니다.");
        map.put("code", HttpStatus.OK);
        
        return map;
    }
    public Map<String, Object> albumGenreConnection(Long albumSeq, Long... genreSeq){
        Map<String, Object> map = new LinkedHashMap<>();
        AlbumInfoEntity album = albumRepo.findById(albumSeq).orElse(null);
        if(album==null){
            map.put("status", false);
            map.put("message", "앨범 정보를 등록했습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        List<GenreInfoEntity> genreList = genreRepo.findIdList(genreSeq);
        if(genreSeq.length!= genreList.size()){
            map.put("status", false);
            map.put("message", "잘못된 장르 번호가 포함되어있습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        List<AlbumGenreConnection> result = new ArrayList<>();
        for(GenreInfoEntity g : genreList){
            AlbumGenreConnection agc = new AlbumGenreConnection(null, g, album);
            result.add(agc);
        }
        agRepo.saveAll(result);
        map.put("status", true);
        map.put("message", "앨범 장르를 저장했습니다");
        map.put("code", HttpStatus.OK);
        
        return map;
    }
}
