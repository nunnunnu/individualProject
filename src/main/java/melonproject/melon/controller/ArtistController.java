package melonproject.melon.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import melonproject.melon.service.ArtistService;
import melonproject.melon.vo.artist.ArtistAddVO;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artService;

    @PutMapping("/add")
    public ResponseEntity<Object> addArtist(@RequestPart ArtistAddVO data, @RequestPart @Nullable MultipartFile file){
        System.out.println("file");
        System.out.println(file);
        Map<String, Object> map = artService.saveArtist(data, file);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
}
