package melonproject.melon.controller;

import static org.springframework.util.StringUtils.*;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import melonproject.melon.error.custom.RequiredValueOmission;
import melonproject.melon.service.PlayListService;
import melonproject.melon.vo.playlist.PlayListInfoVO;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlayListController {
    private final PlayListService pService; 
    
    @PutMapping("/{name}")
    public ResponseEntity<Object> playlistCreate(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String name){
        Map<String, Object> map = pService.createPlayList(userDetails, name);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
        
    }
    @GetMapping
    public ResponseEntity<List<PlayListInfoVO>> showMyPlaylist(@AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(pService.showMyPlaylist(userDetails), HttpStatus.OK);
    }
    @PutMapping("/song/{playlist}/{song}")
    public ResponseEntity<Object> addSong(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long playlist, @PathVariable Long song){
        Map<String, Object> map = pService.playlistAddSong(userDetails, playlist, song);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @GetMapping("/song/{playlist}")
    public ResponseEntity<Object> playListGetSong(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long playlist){
        Map<String, Object> map = pService.showPlayListSongList(playlist, userDetails);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @DeleteMapping("/{seq}")
    public ResponseEntity<Object> playlistDelete(@PathVariable Long seq, @AuthenticationPrincipal UserDetails userDetails){
        Map<String, Object> map = pService.delPlaylist(seq, userDetails);
        return new ResponseEntity<>(map, HttpStatus.OK);    
    }
    @DeleteMapping("/{play}/{order}")
    public ResponseEntity<Object> playlistSongDelete(
            @PathVariable Long play, 
            @PathVariable Integer order, 
            @AuthenticationPrincipal UserDetails userDetails){
        Map<String, Object> map = pService.delPlaylistSong(userDetails, play, order);
        return new ResponseEntity<>(map, HttpStatus.OK);    
    }
}
