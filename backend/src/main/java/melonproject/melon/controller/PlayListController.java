package melonproject.melon.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import melonproject.melon.error.ErrorCode;
import melonproject.melon.error.ErrorResponse;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.RequiredValueOmission;
import melonproject.melon.service.PlayListService;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlayListController {
    private final PlayListService pService; 
    
    @PutMapping("/{name}")
    public ResponseEntity<Object> playlistCreate(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String name){
        System.out.println(userDetails);
        Map<String, Object> map = pService.createPlayList(userDetails, name);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
        
    }
}
