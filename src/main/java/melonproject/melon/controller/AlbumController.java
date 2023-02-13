package melonproject.melon.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/new")
    public ResponseEntity<Object> newAlbum(@PageableDefault(size=20, sort="albumRegDt",direction = Sort.Direction.ASC) Pageable  page){
        
        System.out.println("page:"+page);
        Map<String, Object> map = aService.newAlbumList(page, LocalDate.now());
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
}
