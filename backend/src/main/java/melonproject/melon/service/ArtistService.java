package melonproject.melon.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.ArtistConnectionEntity;
import melonproject.melon.entity.artist.ArtistGroupInfoEntity;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.ArtistSoloInfoEntity;
import melonproject.melon.entity.info.AgencyInfoEntity;
import melonproject.melon.entity.user.ArtistFanEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.NotFoundArtist;
import melonproject.melon.repository.artist.ArtistConnectionRepository;
import melonproject.melon.repository.artist.ArtistGroupInfoRepository;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.ArtistSoloInfoRepository;
import melonproject.melon.repository.info.AgencyInfoRepository;
import melonproject.melon.repository.user.ArtistFanRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.vo.artist.ArtistAddVO;
import melonproject.melon.vo.artist.ArtistChannelVO;
import melonproject.melon.vo.artist.ArtistDetailVO;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistInfoRepository aRepo;
    private final FileService fService;
    private final AgencyInfoRepository aiRepo;
    private final ArtistSoloInfoRepository asRepo;
    private final ArtistGroupInfoRepository agRepo;
    private final ArtistConnectionRepository acRepo;
    private final MemberInfoRepository mRepo;
    private final ArtistFanRepository afRepo;

    public Map<String, Object> saveArtist(ArtistAddVO data, MultipartFile file) throws AmazonServiceException, SdkClientException, IOException{
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

    public Map<String, Object> artistChannel(Long artistSeq, String type, UserDetails userDetails){
        Map<String, Object> map = new LinkedHashMap<>();
        if(type.equals("login") && userDetails==null){
            map.put("status", false);
            map.put("message", "만료된 토큰입니다.");
            map.put("code", HttpStatus.FORBIDDEN);
            return map;
        }
        
        ArtistInfoEntity artist = aRepo.findById(artistSeq).orElse(null);
        if(artist==null){
            map.put("status",false);
            map.put("message","아티스트 번호가 잘못되었습니다.");
            map.put("code",HttpStatus.BAD_REQUEST);
            return map;
        }
        MemberInfoEntity member = null;
        if(userDetails!=null){
            member = mRepo.findByMiId(userDetails.getUsername());
            if(member==null){
                throw new MemberNotFound();
            }
        }
        ArtistChannelVO channelVO = new ArtistChannelVO(artist);
        if(channelVO.getType().equals("솔로")){
            List<ArtistConnectionEntity> connection = acRepo.findBySolo(artist);
            for(ArtistConnectionEntity ac : connection){
                channelVO.groupSetting(ac.getGroup());
            }
            
            
        }else if(channelVO.getType().equals("그룹")){
            List<ArtistConnectionEntity> connection = acRepo.findByGroup(artist);
            for(ArtistConnectionEntity ac : connection){
                channelVO.soloSetting(ac.getSolo());
            }
        }
        
        channelVO.setIsFan(afRepo.existsByArtistAndMember(artist, member));

        map.put("status",true);
        map.put("message","조회성공");
        map.put("code",HttpStatus.OK);
        map.put("data", channelVO);
        return map;
    }

    public Map<String, Object> artistDetailPage(Long seq){
        Map<String, Object> map = new LinkedHashMap<>();
        
        // ArtistInfoEntity artist =aRepo.findById(seq).orElse(null);
        // if(artist==null){
        //   
        // }
        ArtistSoloInfoEntity solo = asRepo.findById(seq).orElse(null);
        ArtistDetailVO adVO = null;
        if(solo!=null){
            adVO = new ArtistDetailVO(solo);
            List<ArtistConnectionEntity> connetion = acRepo.findBySolo(solo);
            for(ArtistConnectionEntity a : connetion){
                adVO.addArtists(a.getGroup());
            }
        }else{
            ArtistGroupInfoEntity group = agRepo.findById(seq).orElse(null);
            if(group == null){
                map.put("status",false);
                map.put("message","아티스트 번호가 잘못되었습니다.");
                map.put("code",HttpStatus.BAD_REQUEST);
                return map;
            }
            adVO = new ArtistDetailVO(group);
            List<ArtistConnectionEntity> connetion = acRepo.findByGroup(group);
            for(ArtistConnectionEntity a : connetion){
                adVO.addArtists(a.getSolo());
            }
        }
        map.put("status",true);
        map.put("message","조회성공");
        map.put("code",HttpStatus.OK);
        map.put("data", adVO);

        return map;
    }
    public Map<String, Object> putFan(UserDetails userDetails, Long seq){
        Map<String, Object> map = new LinkedHashMap();
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        ArtistInfoEntity artist = aRepo.findById(seq).orElseThrow(()->new NotFoundArtist());

        ArtistFanEntity fan = afRepo.findByArtistAndMember(artist, member);
        Boolean check = true;
        if(fan==null){
            fan = new ArtistFanEntity(seq, artist, member, LocalDateTime.now());
            afRepo.save(fan);
            check = false;
        }else{
            afRepo.delete(fan);
        }
        map.put("status", true);
        map.put("message", (check?"취소":"팬맺기")+" 성공");

        return map;
    }
}

