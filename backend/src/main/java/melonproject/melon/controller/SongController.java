package melonproject.melon.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.song.SoundQuality;
import melonproject.melon.service.SongService;
import melonproject.melon.vo.song.SongAddVO;
import melonproject.melon.vo.song.Creator.SongCreatorAddVO;

@RestController
@RequiredArgsConstructor
@RequestMapping("song")
public class SongController {
    private final SongService songService;

    @PutMapping("/add")
    public ResponseEntity<Object> addSong(@RequestBody SongAddVO data){
        Map<String, Object> map = songService.songAdd(data);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    
    @PutMapping("/creator")
    public ResponseEntity<Object> songCreator(@RequestBody SongCreatorAddVO data){
        Map<String, Object> map = songService.songCreatorConnection(data);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @PutMapping("/add/file/{seq}")
    public ResponseEntity<Object> songFileSave(@PathVariable Long seq, @RequestParam SoundQuality sound, @RequestPart MultipartFile file){
        Map<String, Object> map = songService.songFileAdd(seq, sound, file);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
        
    }
    @GetMapping("/detail/{seq}")
    public ResponseEntity<Object> songDetail(@PathVariable Long seq){
        Map<String, Object> map = songService.songDetailShow(seq);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
        
    }
    
    @GetMapping("/artist/part/{seq}")
    public ResponseEntity<Object> artistSongPart(@PathVariable Long seq,
        @RequestParam @PageableDefault(size=10, sort="siRegDt",direction = Sort.Direction.ASC) @Nullable Pageable page,
        @AuthenticationPrincipal UserDetails userDetails
    ){
        Map<String, Object> map = songService.artistSongParticipation(seq, page, userDetails);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
        
    }
    @GetMapping("/new")
    public ResponseEntity<Object> newSongList(
        @PageableDefault(size=2, sort="siRegDt",direction = Sort.Direction.ASC) @Nullable Pageable page,
        @AuthenticationPrincipal UserDetails userDetails
    ){
        Map<String, Object> map = songService.newSongList(page, userDetails);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }

    
    

}