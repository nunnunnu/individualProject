package melonproject.melon.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.security.provider.JwtTokenProvider;
import melonproject.melon.security.service.UserDetailsImpl;
import melonproject.melon.security.vo.TokenVO;
import melonproject.melon.vo.Member.chartVO.social.SocialUserInfoVO;

@Service
@RequiredArgsConstructor
public class KaKaoUserService {
    private final PasswordEncoder passwordEncoder;
    private final MemberInfoRepository memberInfoRepository;
    private final JwtTokenProvider jwtTokenProvider; 
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisService redisService;

    public SocialUserInfoVO kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        SocialUserInfoVO kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 카카오ID로 회원가입 처리
        MemberInfoEntity kakaoUser = registerKakaoUserIfNeed(kakaoUserInfo);

        // 4. 강제 로그인 처리
        Authentication authentication = forceLogin(kakaoUser);

        UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(kakaoUser.getMiId(), kakaoUser.getMiPwd());
        
        authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // String accessToken = jwtTokenProvider.generateToken(authentication).getAccessToken();
        String refreshToken = jwtTokenProvider.generateToken(authentication).getRefreshToken();

        redisService.setValues(refreshToken, kakaoUser.getMiId());

        kakaoUserInfo.setToken(jwtTokenProvider.generateToken(authentication));
        
        return kakaoUserInfo;
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "4c2ba3938a53b9849aef10abf1a2283e");
        body.add("redirect_uri", "http://localhost:8080/kakao");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }
    private SocialUserInfoVO getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        System.out.println(jsonNode);

        Long seq = jsonNode.get("id").asLong();
        String id = jsonNode.get("id").asText();
        System.out.println(id);
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        return new SocialUserInfoVO(seq, nickname, id);
    }
    private MemberInfoEntity registerKakaoUserIfNeed (SocialUserInfoVO kakaoUserInfo) {
        // DB 에 중복된 email이 있는지 확인
        String kakaoId = kakaoUserInfo.getId();
        String nickname = kakaoUserInfo.getNickname();
        MemberInfoEntity kakaoUser = memberInfoRepository.findByMiId(kakaoId);

        if (kakaoUser == null) {
            // 회원가입
            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            String profile = "https://ossack.s3.ap-northeast-2.amazonaws.com/basicprofile.png";

            kakaoUser = new MemberInfoEntity(null, kakaoId, encodedPassword, null, null, null, null, null, LocalDateTime.now(), "USER", 1, nickname);

            memberInfoRepository.save(kakaoUser);

        }
        return kakaoUser;
    }

    private Authentication forceLogin(MemberInfoEntity kakaoUser) {
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    // 5. response Header에 JWT 토큰 추가
    private void kakaoUsersAuthorizationInput(Authentication authentication, HttpServletResponse response) {
        // response header에 token 추가
        TokenVO token = jwtTokenProvider.generateToken(authentication);
        response.addHeader("Authorization", "BEARER" + " " + token);
    }
}
