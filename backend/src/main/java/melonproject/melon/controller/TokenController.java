package melonproject.melon.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import melonproject.melon.service.AlbumService;
import melonproject.melon.service.MemberService;
import melonproject.melon.service.PlayListService;
import melonproject.melon.service.SongService;
import melonproject.melon.vo.Member.RefreshCheck;

@RestController
@RequiredArgsConstructor
// @RequestMapping("")
public class TokenController {
    private final MemberService mService;
    private final AlbumService aService;
    private final SongService sService;

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
    @GetMapping("/listen/genre")
    public ResponseEntity<Object> genreListenCount(@AuthenticationPrincipal UserDetails userDetails){
        Map<String, Object> map = mService.genreListen(userDetails);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @PutMapping("/grade/{album}")
    public ResponseEntity<Object> putGrade(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long album, @RequestParam Double grade){
        Map<String, Object> map = aService.setAlbumGrade(userDetails, album, grade);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @GetMapping("/like/{song}")
    public ResponseEntity<Object> getAlbumDetail(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long song){
        Map<String, Object> map = sService.checkLikeSong(userDetails, song);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @PostMapping("/likeUnlike/{song}")
    public ResponseEntity<Object> LikedUnLiked(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long song){
        Map<String, Object> map = sService.LikeUnLike(userDetails, song);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @PostMapping("/refreshDel")
    public ResponseEntity<Object> logout(@RequestBody RefreshCheck refresh){
        System.out.println("sssss");
        System.out.println(refresh);
        Map<String, Object> map = mService.logout(refresh.getRefresh());
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
        
    }

}
