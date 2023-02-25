package melonproject.melon.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import melonproject.melon.service.MemberService;
import melonproject.melon.vo.Member.MemberJoinVO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService mService;

    @PostMapping("/join")
    public ResponseEntity<Object> memberJoin(@RequestBody MemberJoinVO data) throws Exception{
        Map<String, Object> map = mService.memberJoin(data);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
}
