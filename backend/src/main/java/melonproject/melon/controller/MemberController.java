package melonproject.melon.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import melonproject.melon.service.MemberService;
import melonproject.melon.vo.Member.LoginVO;
import melonproject.melon.vo.Member.MemberJoinVO;
import melonproject.melon.vo.Member.RefreshCheck;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService mService;

    @PutMapping("/join")
    public ResponseEntity<Object> memberJoin(@RequestBody MemberJoinVO data) throws Exception{
        System.out.println(data);
        Map<String, Object> map = mService.memberJoin(data);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @PostMapping("/login")
    public ResponseEntity<Object> memberLogin(@RequestBody @Valid LoginVO data) throws Exception{
        System.out.println(data);
        Map<String, Object> map = mService.login(data);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @GetMapping("/id")
    public ResponseEntity<Object> duplicate(@RequestParam String id){
        Map<String, Object> map = mService.isDuplicate(id);
        
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @PostMapping("refresh")
    public ResponseEntity<Object> refresh(@RequestBody RefreshCheck data){
        System.out.println(data);
        Map<String, Object> map = mService.accessToken(data);
        return new ResponseEntity<>(map, (HttpStatus)map.get(("code")));
    }



}
