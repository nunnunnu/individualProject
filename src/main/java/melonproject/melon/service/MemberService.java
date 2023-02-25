package melonproject.melon.service;

import static org.springframework.util.StringUtils.hasText;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.util.AESAlgorithm;
import melonproject.melon.vo.Member.LoginVO;
import melonproject.melon.vo.Member.MemberJoinVO;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberInfoRepository mRepo;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public Map<String, Object> memberJoin(MemberJoinVO data) throws Exception{
        Map<String, Object> map = new LinkedHashMap<>();
        
        if(!hasText(data.getId()) || !hasText(data.getPwd())){
            map.put("status", false);
            map.put("message", "아이디 혹은 비밀번호 누락");
            map.put("code", HttpStatus.BAD_GATEWAY);
            return map;
        }
        
        if(mRepo.countByMiId(data.getId())>=1){
            map.put("status", false);
            map.put("message", "이미 가입된 아이디입니다.");
            map.put("code", HttpStatus.BAD_GATEWAY);
            return map;
        }
        MemberInfoEntity member = MemberInfoEntity.builder().miId(data.getId())
                        .miPwd(AESAlgorithm.Encrypt(data.getPwd()))
                        .miName(data.getName())
                        .miAge(data.getAge())
                        .miPhone(data.getPhone())
                        .miEmail(data.getEmail())
                        .miBirth(data.getBirth())
                        .miRegDt(data.getRegDt())
                        .build();
        mRepo.save(member);
        map.put("status", true);
        map.put("message", "성공.");
        map.put("code", HttpStatus.OK);
        return map;
    }

    @Transactional
    public Map<String, Object> login(LoginVO login) throws Exception{
        Map<String, Object> map = new LinkedHashMap<>();

        login.setPwd(AESAlgorithm.Encrypt(login.getPwd()));
        MemberInfoEntity member = mRepo.findByMiIdAndMiPwd(login.getId(), login.getPwd());
        if(member==null){
            map.put("status", false);
            map.put("message", "아이디 또는 비밀번호 에러입니다.");
            map.put("code", HttpStatus.UNAUTHORIZED);
            return map;
        } else if (!member.isEnabled()) {
            map.put("status", false);
            map.put("message", "이용정지");
            map.put("code", HttpStatus.UNAUTHORIZED);
            return map;
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getMiId(), member.getMiPwd());
    
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    

        return map;
    }
}
