package melonproject.melon.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import melonproject.melon.service.SongService;
import melonproject.melon.vo.song.SongAddVO;

@RestController
@RequiredArgsConstructor
@RequestMapping("song")
public class SongController {
    private final SongService songService;

    @GetMapping("/add")
    public ResponseEntity<Object> addSong(@RequestBody SongAddVO data){
        Map<String, Object> map = songService.songAdd(data);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }

}
