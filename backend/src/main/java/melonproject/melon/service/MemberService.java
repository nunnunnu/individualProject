package melonproject.melon.service;

import static org.springframework.util.StringUtils.hasText;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import melonproject.melon.Validator.SignUpFormValidator;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.TicketMemberEntity;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.user.HistoryPlayRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.repository.user.TicketMemberRepository;
import melonproject.melon.security.config.WebSecurityConfig;
import melonproject.melon.security.provider.JwtTokenProvider;
import melonproject.melon.vo.Member.LoginVO;
import melonproject.melon.vo.Member.MemberInfo;
import melonproject.melon.vo.Member.MemberJoinVO;
import melonproject.melon.vo.Member.MemberListenSong;
import melonproject.melon.vo.Member.RefreshCheck;
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
    private final RedisTemplate<String, Object> redisTemplate;
    private final WebSecurityConfig wConfig;
    // private final TmRepo tmRepo;
    private final RedisService redisService;
    private final SignUpFormValidator signUpFormValidator;

    private Boolean checkPassword(String rawPassword, String findMemberPassword) {
        if (!wConfig.passwordEncoder().matches(rawPassword, findMemberPassword)) {
            return false;
        }
        return true;
    }

    @Transactional
    public Map<String, Object> memberJoin(MemberJoinVO data,BindingResult bindingResult){
        signUpFormValidator.validate(data, bindingResult);

        Map<String, Object> map = new LinkedHashMap<>();

        // MemberInfoEntity member = MemberInfoEntity.builder().miId(data.getId())
        //                 .miPwd(wConfig.passwordEncoder().encode(data.getPwd()))
        //                 .miName(data.getName())
        //                 .miAge(data.getAge())
        //                 .miPhone(data.getPhone())
        //                 .miEmail(data.getEmail())
        //                 .miBirth(data.getBirth())
        //                 .miRegDt(data.getRegDt())
        //                 .miNickName(data.getNickName())
        //                 .build();
        // mRepo.save(member);
        // map.put("status", true);
        map.put("message", "회원가입 완료. 로그인페이지로 이동합니다.");
        map.put("code", HttpStatus.OK);
        return map;
    }

    @Transactional
    public Map<String, Object> login(LoginVO login){
        Map<String, Object> map = new LinkedHashMap<>();
        
        if(!hasText(login.getId()) || !hasText(login.getPwd())){
            map.put("status", false);
            map.put("message", "아이디와 비밀번호 모두 입력해주세요.");
            map.put("code", HttpStatus.UNAUTHORIZED);
            return map;
        }

        // login.setPwd(AESAlgorithm.Encrypt(login.getPwd()));
        MemberInfoEntity member = mRepo.findByMiId(login.getId());
        if(member == null || !checkPassword(login.getPwd(), member.getPassword())){
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

        String accessToken = jwtTokenProvider.generateToken(authentication).getAccessToken();
        String refreshToken = jwtTokenProvider.generateToken(authentication).getRefreshToken();

        redisService.setValues(refreshToken, member.getMiId());
        // redisTemplate.opsForHash().put(member.getMiId(), "accessToken", accessToken);
        // redisTemplate.opsForHash().put(member.getMiId(), "refreshToken", refreshToken);
        // redisTemplate.opsForHash().put(member.getMiId(), "accessToken", accessToken);
        // redisTemplate.opsForHash().put(member.getMiId(), refreshToken);

        // ValueOperations<String, String> values = redisTemplate.opsForValue();
        // values.set(member.getMiId(), refreshToken);

        map.put("status", true);
        map.put("message", "로그인 완료");
        map.put("code", HttpStatus.OK);
        map.put("token", jwtTokenProvider.generateToken(authentication));
        map.put("member", member.getMiId());

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
        if(mRepo.existsByMiId(id)){
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
    public Map<String, Object> accessToken(RefreshCheck data){
        Map<String, Object> map = new LinkedHashMap<>();
        // String refreshToken = (String) redisTemplate.opsForHash().get(data.getId(), "refreshToken");
        System.out.println("refreshToken");
        String id = redisService.getValues(data.getRefresh());
        System.out.println(id);
        if(!StringUtils.hasText(id)){
            map.put("message","해당 해원은 로그인 한적 없는 회원입니다.");
            map.put("code",HttpStatus.BAD_REQUEST);
            map.put("status",false);
            return map;
        }
        
        if(jwtTokenProvider.isRefreshTokenExpired(data.getRefresh())){
            redisService.delValues(data.getRefresh());
            map.put("message","만료된 토큰");
            map.put("code",HttpStatus.BAD_REQUEST);
            map.put("status",false);
            return map;
        }
        MemberInfoEntity member = mRepo.findByMiId(id);
        UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(member.getMiId(), member.getMiPwd());
        
        Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        
        String accessToken = jwtTokenProvider.generateToken(authentication).getAccessToken();
        
        redisTemplate.opsForHash().put(member.getMiId(), "accessToken", accessToken);
        
        map.put("status", true);
        map.put("message", "재발급 완료");
        map.put("code", HttpStatus.OK);
        map.put("token", accessToken);
        return map;
    }
    public Map<String, Object> logout(String refresh){
        Map<String, Object> map = new LinkedHashMap<>();
        if(redisService.getValues(refresh)==null){
            map.put("status", false);
            map.put("message", "존재하지않는 토큰입니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
        redisService.delValues(refresh);
        map.put("status", true);
        map.put("message", "삭제 완료");
        map.put("code", HttpStatus.OK);

        return map;
    }
}
