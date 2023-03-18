package melonproject.melon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import melonproject.melon.service.KaKaoUserService;
import melonproject.melon.vo.Member.chartVO.social.SocialUserInfoVO;

@RestController
@RequiredArgsConstructor
public class SocialLoginController {

    private final KaKaoUserService kService;
    
    @GetMapping("/kakao")
    public SocialUserInfoVO  kakaoCallback(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        System.out.println(code);
        return kService.kakaoLogin(code, response);

    }
}
