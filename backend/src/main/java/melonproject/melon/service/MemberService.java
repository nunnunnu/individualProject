package melonproject.melon.service;

import static org.springframework.util.StringUtils.hasText;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.TicketMemberEntity;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.user.HistoryPlayRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.repository.user.TicketMemberRepository;
import melonproject.melon.security.provider.JwtTokenProvider;
import melonproject.melon.util.AESAlgorithm;
import melonproject.melon.vo.Member.LoginVO;
import melonproject.melon.vo.Member.MemberInfo;
import melonproject.melon.vo.Member.MemberJoinVO;
import melonproject.melon.vo.Member.MemberListenSong;
import melonproject.melon.vo.Member.chartVO.ChartVO;
import melonproject.melon.vo.Member.chartVO.UserGenreVO;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberInfoRepository mRepo;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TicketMemberRepository tmRepo;
    private final SongInfoRepository sRepo;
    private final HistoryPlayRepository hpRepo;

    @Transactional
    public Map<String, Object> memberJoin(MemberJoinVO data) throws Exception{
        Map<String, Object> map = new LinkedHashMap<>();
        String emailPattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        String phonePattern = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
        String passwordPattern = "^[a-zA-Z\\d`~!@#$%^&*()-_=+]{6,}$";
        if(!hasText(data.getId()) || !hasText(data.getPwd())){
            map.put("status", false);
            map.put("message", "아이디 혹은 비밀번호 누락");
            map.put("code", HttpStatus.OK);
            return map;
        }
        if(!hasText(data.getNickName())){
            map.put("status", false);
            map.put("message", "닉네임을 입력하지 않으셨습니다.");
            map.put("code", HttpStatus.OK);
            return map;
        }
        if(!Pattern.matches(passwordPattern, data.getPwd())){ //공백없이 특수문자 가능 6자리 이상
            map.put("status", false);
            map.put("message", "비밀번호는 공백없이 6자리 이상 가능합니다.");
            map.put("code", HttpStatus.OK);
            return map;
        }
        if(data.getEmail()!=null && !Pattern.matches(emailPattern, data.getEmail())){
            map.put("status", false);
            map.put("message", "올바른 이메일 형식이 아닙니다. 이메일을 다시 확인해주세요.");
            map.put("code", HttpStatus.OK);
            return map;
        }
        if(data.getPhone()!=null && !Pattern.matches(phonePattern, data.getPhone())){
            map.put("status", false);
            map.put("message", "올바른 전화번호 형식이 아닙니다. 번호를 다시 확인해주세요.");
            map.put("code", HttpStatus.OK);
            return map;
        }
        if(mRepo.countByMiId(data.getId())>=1){
            map.put("status", false);
            map.put("message", "이미 가입된 아이디입니다.");
            map.put("code", HttpStatus.OK);
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
                        .miNickName(data.getNickName())
                        .build();
        mRepo.save(member);
        map.put("status", true);
        map.put("message", "회원가입 완료. 로그인페이지로 이동합니다.");
        map.put("code", HttpStatus.OK);
        return map;
    }

    @Transactional
    public Map<String, Object> login(LoginVO login) throws Exception{
        Map<String, Object> map = new LinkedHashMap<>();
        
        if(!hasText(login.getId()) || !hasText(login.getPwd())){
            map.put("status", false);
            map.put("message", "아이디와 비밀번호 모두 입력해주세요.");
            map.put("code", HttpStatus.UNAUTHORIZED);
            return map;
        }

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
        map.put("status", true);
        map.put("message", "로그인 완료");
        map.put("code", HttpStatus.OK);
        map.put("token", jwtTokenProvider.generateToken(authentication));

        return map;
    }

    public Map<String, Object> isDuplicate(String id) {
        Map<String, Object> map = new LinkedHashMap<>();
        if(!hasText(id)){
            map.put("status", false);
            map.put("message", "아이디를 입력하지 않으셨습니다.");
            map.put("code", HttpStatus.OK);
            return map;
        }
        if(mRepo.countByMiId(id)>=1){
            map.put("status", false);
            map.put("message", "중복 아이디입니다.");
            map.put("code", HttpStatus.OK);
            return map;    
        }
        map.put("status", true);
        map.put("message", "사용가능한 아이디입니다.");
        map.put("code", HttpStatus.OK);

        return map;
    }

    public Map<String, Object> findUser(UserDetails userDetail) {
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = mRepo.findByMiId(userDetail.getUsername());
        if(member==null){
            map.put("status", false);
            map.put("message", "토큰정보 에러같음??");
            map.put("code", HttpStatus.OK);
            return map;
        }
        LocalDate start = LocalDate.now().withDayOfMonth(1);     
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());  
        TicketMemberEntity ticket = tmRepo.findByMemberAndTmRegDtBetween(member, start, end);
        MemberInfo result = new MemberInfo(member, ticket);
        map.put("status", true);
        map.put("message", "성공");
        map.put("code", HttpStatus.OK);
        map.put("data", result);
        return map;
    }
    public Map<String, Object> songListen(Long seq, UserDetails userDetails){
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            map.put("status", false);
            map.put("message", "정상적인 접근이 아닙니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        SongInfoEntity song = sRepo.findById(seq).orElse(null);
        if(song==null){
            map.put("status", false);
            map.put("message", "곡 번호 에러");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        MemberListenSong result = hpRepo.listenCount(member, song);
        map.put("status", true);
        map.put("message", "조회완료");
        map.put("code", HttpStatus.OK);
        map.put("data", result);
        return map;
    }

    public Map<String, Object> genreListen(UserDetails userDetails) {
        Map<String, Object> map = new LinkedHashMap<>();
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            map.put("status", false);
            map.put("message", "회원인증 에러");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        List<UserGenreVO> vos = hpRepo.countListenGenre(member);
        ChartVO chart = new ChartVO(vos);
        
        map.put("status", true);
        map.put("message", "성공");
        map.put("code", HttpStatus.OK);
        map.put("data", chart);
        return map;
    }
}