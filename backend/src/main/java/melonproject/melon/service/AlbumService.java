package melonproject.melon.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.album.AlbumGenreConnection;
import melonproject.melon.entity.artist.album.AlbumGradeEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.info.AgencyInfoEntity;
import melonproject.melon.entity.info.GenreInfoEntity;
import melonproject.melon.entity.info.PublisherInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.reader.MemberReader;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.album.AlbumGenreConnectionRepository;
import melonproject.melon.repository.artist.album.AlbumGradeRepository;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.info.AgencyInfoRepository;
import melonproject.melon.repository.info.GenreInfoRepository;
import melonproject.melon.repository.info.PublisherInfoRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.repository.user.SongLikesRepository;
import melonproject.melon.vo.album.AlbumAddVO;
import melonproject.melon.vo.album.AlbumDetailVO;
import melonproject.melon.vo.album.AlbumNewListVO;
import melonproject.melon.vo.album.ArtistAlbumVO;
import melonproject.melon.vo.album.NewAlbum;

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
    private final MemberInfoRepository mRepo;
    private final AlbumGradeRepository gradeRepo;
    private final SongInfoRepository songRepo;
    private final SongLikesRepository slRepo;
    private final MemberReader memberReader;

    public Map<String, Object> addAlbum(AlbumAddVO data, MultipartFile file) throws AmazonServiceException, SdkClientException, IOException{
        Map<String, Object> map = new LinkedHashMap<>();
        if(data.getType()==null){
            map.put("status", false);
            map.put("message", "앨범 타입 오류");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        if(data.getRegDt()==null || data.getAgency()==null || data.getName()==null || data.getPublisher()==null || data.getType()==null){
            map.put("status", false);
            map.put("message", "필수 값을 모두 입력해주세요");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        PublisherInfoEntity pub = pubRepo.findById(data.getPublisher()).orElse(null);
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
            map.put("message", "앨범 번호 오류입니다.");
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

    public Map<String, Object> newAlbumList(Pageable page, LocalDate date){
        Map<String, Object> map = new LinkedHashMap<>();

        Page<AlbumInfoEntity> albums = albumRepo.findByalbumRegDtLessThanEqual(page,date);
        
        Page<AlbumNewListVO> result = albums.map(a->new AlbumNewListVO(a));

        map.put("status", true);
        map.put("data", result);
        map.put("code", HttpStatus.OK);

        return map;
    }

    public Map<String, Object> setAlbumGrade(UserDetails userDetails, Long albumSeq, Double grade){
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = memberReader.findByMemberIdNotFoundError(userDetails.getUsername());
        AlbumInfoEntity album = albumRepo.findById(albumSeq).orElse(null);
        if(album==null){
            map.put("status", false);
            map.put("message", "앨범 번호 오류입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        if(gradeRepo.countByAlbumAndMember(album, member) >=1){
            map.put("status", false);
            map.put("message", "이미 평점을 등록한 앨범입니다.");
            map.put("code", HttpStatus.ACCEPTED);
            return map;

        }
        AlbumGradeEntity entity = new AlbumGradeEntity(null, album, grade, member);
        gradeRepo.save(entity);
        map.put("status", true);
        map.put("message", "평점이 등록되었습니다.");
        map.put("code", HttpStatus.OK);
        return map;
    }

    // public Map<String, Object> albumDetail(Long albumSeq){
    //     Map<String, Object> map = new LinkedHashMap<>();
    //     AlbumInfoEntity album = albumRepo.findAllFetch(albumSeq);
    //     if(album==null){
    //         map.put("status", false);
    //         map.put("message", "앨범 번호 오류입니다.");
    //         map.put("code", HttpStatus.BAD_REQUEST);
    //         return map;
    //     }
    //     AlbumDetailVO albumVo = new AlbumDetailVO(album);
    //     albumVo.addSongList(album.getSongList());
    //     albumVo.setGrade(gradeRepo.albumGrade(album));
    //     // List<SongInfoEntity> songs = songRepo.findByAlbum(album);
        
    //     map.put("status", true);
    //     map.put("message", "조회성공");
    //     map.put("code", HttpStatus.OK);
    //     map.put("data", albumVo);

    //     return map;
    // }
    
    public Map<String, Object> artistAlbum(Long seq, Pageable page){
        Map<String, Object> map = new LinkedHashMap<>();
        ArtistInfoEntity artist = artRepo.findById(seq).orElse(null);
        if(artist==null){
            map.put("status", false);
            map.put("message", "앨범 번호 오류입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }

        Page<AlbumInfoEntity> albums = albumRepo.findByArtist(artist, page);
        if(albums.getContent().size()==0){
            map.put("status", false);
            map.put("message", "조회할 값이 없음");
            map.put("code", HttpStatus.NO_CONTENT);
            return map;
        }
        Page<ArtistAlbumVO> result = albums.map(a->new ArtistAlbumVO(a, songRepo.findTop1ByAlbumAndSiTitle(a, true)));
        map.put("status", true);
        map.put("message", "조회성공");
        map.put("code", HttpStatus.OK);
        map.put("data", result);

        return map;
    }
    public Map<String, Object> newAlbumTwo(){
        Map<String, Object> map = new LinkedHashMap<>();
        List<AlbumInfoEntity> newAlbums = albumRepo.findTop2ByOrderByAlbumRegDtDesc();
        List<NewAlbum> result = new ArrayList<>();
        for(AlbumInfoEntity a : newAlbums){
            result.add(new NewAlbum(a));
        }
        map.put("status", true);
        map.put("message", "조회성공");
        map.put("code", HttpStatus.OK);
        map.put("data", result);
        return map;
    }
    public Map<String, Object> albumDetail(UserDetails userDetails, Long seq, String type) {
        Map<String, Object> map = new LinkedHashMap<>();
        if(type.equals("login") && userDetails==null){
            map.put("status", false);
            map.put("message", "만료된 토큰입니다.");
            map.put("code", HttpStatus.FORBIDDEN);
            return map;
        }
        AlbumInfoEntity album = albumRepo.findAllFetch(seq);
        if(album==null){
            map.put("status", false);
            map.put("message", "앨범 번호 오류입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        AlbumDetailVO albumVo = new AlbumDetailVO(album);
        albumVo.setGrade(gradeRepo.albumGrade(album));
        
        if(userDetails!=null){
            MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
            if(member==null){
                map.put("status", false);
                map.put("message", "정상적인 접근이 아닙니다.");
                map.put("code", HttpStatus.BAD_REQUEST);
                return map;
            }
            for(SongInfoEntity s : album.getSongList()){
                Boolean songliked = slRepo.countBySongAndMember(s, member)>=1?true:false;
                albumVo.putSongList(s, songliked);
            }
        }else{
            albumVo.addSongList(album.getSongList());
        }
        
        map.put("status", true);
        map.put("message", "조회성공");
        map.put("code", HttpStatus.OK);
        map.put("data", albumVo);
        return map;
    }
}
