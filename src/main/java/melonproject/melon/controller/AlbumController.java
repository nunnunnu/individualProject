package melonproject.melon.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import melonproject.melon.service.AlbumService;
import melonproject.melon.vo.album.AlbumAddVO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {
    private final AlbumService aService;

    @PutMapping("/add")
    public ResponseEntity<Object> addAlbum(@RequestPart AlbumAddVO data, @RequestPart MultipartFile file){
        Map<String, Object> map = aService.addAlbum(data, file);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @PutMapping("/genre")
    public ResponseEntity<Object> addAlbumGenre(@RequestParam Long album, @RequestParam Long... genre){
        Map<String, Object> map = aService.albumGenreConnection(album, genre);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
}
