package melonproject.melon.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import melonproject.melon.service.MemberService;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final MemberService mService;

    @GetMapping("/myInfo")
    public ResponseEntity<Object> findUser(@AuthenticationPrincipal UserDetails userDetails){
        Map<String, Object> map = mService.findUser(userDetails);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @GetMapping("/listen/song/{seq}")
    public ResponseEntity<Object> songListenCount(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long seq){
        Map<String, Object> map = mService.songListen(seq, userDetails);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
