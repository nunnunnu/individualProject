package melonproject.melon.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.playlist.PlayListInfoEntity;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.RequiredValueOmission;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.repository.user.playlist.PlayListInfoRepository;
@Service
@RequiredArgsConstructor
public class PlayListService {
    
    private final MemberInfoRepository mRepo;
    private final PlayListInfoRepository pRepo;

    public Map<String, Object> createPlayList(UserDetails userDetails, String name) {
        Map<String, Object> map = new LinkedHashMap<>();
        if(!StringUtils.hasText(name)){
            throw new RequiredValueOmission();
        }
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        PlayListInfoEntity playlist = new PlayListInfoEntity(null, name, member);
        
        pRepo.save(playlist);

        map.put("status", false);
        map.put("message", "저장성공");
        map.put("code", HttpStatus.OK);

        return map;
    }
}
