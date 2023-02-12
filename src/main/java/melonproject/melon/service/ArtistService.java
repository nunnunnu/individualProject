package melonproject.melon.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.ArtistGroupInfoEntity;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.ArtistSoloInfoEntity;
import melonproject.melon.entity.info.AgencyInfoEntity;
import melonproject.melon.repository.artist.ArtistGroupInfoRepository;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.ArtistSoloInfoRepository;
import melonproject.melon.repository.info.AgencyInfoRepository;
import melonproject.melon.vo.artist.ArtistAddVO;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistInfoRepository aRepo;
    private final FileService fService;
    private final AgencyInfoRepository aiRepo;
    private final ArtistSoloInfoRepository asRepo;
    private final ArtistGroupInfoRepository agRepo;

    public Map<String, Object> saveArtist(ArtistAddVO data, MultipartFile file){
        System.out.println(data);
        Map<String, Object> map = new LinkedHashMap<>();
        if(data.getName()==null || data.getDtype()==null){
            map.put("status", false);
            map.put("message", "필수 값을 모두 입력해주세요");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        AgencyInfoEntity agency = null;
        if(data.getAgency()!=null){
            agency = aiRepo.findById(data.getAgency()).orElse(null);
            if(agency==null){
                map.put("status", false);
                map.put("message", "소속사 번호가 잘못되었습니다.");
                map.put("code", HttpStatus.BAD_REQUEST);
                return map;
            }
        }
        
        if(data.getDtype().equals("SOLO")){
            ArtistSoloInfoEntity solo = fService.saveArtistSoloFile(file, data.getName());
            solo.addSetting(agency, data.getBirth(), data.getCountry(), data.getDebut(), data.getName());
            System.out.println(solo.getArtSeq());
            if(data.getBirth()==null){
                solo.setArtSeq(solo.getArtSeq());
            }else{

            }
            asRepo.save(solo);
            map.put("status", true);
            map.put("message", "솔로 아티스트 정보가 입력되었습니다.");
            map.put("code", HttpStatus.OK);
        }else if(data.getDtype().equals("GROUP")){
            ArtistGroupInfoEntity group = fService.saveArtistGroupFile(file, data.getName());
            group.addSetting(agency, data.getCountry(), data.getDebut(), data.getName(), data.getSplit());
            agRepo.save(group);
            map.put("status", true);
            map.put("message", "그룹 아티스트 정보가 입력되었습니다.");
            map.put("code", HttpStatus.OK);
        }
        return map;
        
    }
}

