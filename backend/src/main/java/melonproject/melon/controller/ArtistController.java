package melonproject.melon.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

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
    public ResponseEntity<Object> addArtist(@RequestPart ArtistAddVO data, @RequestPart @Nullable MultipartFile file) throws AmazonServiceException, SdkClientException, IOException{
        Map<String, Object> map = artService.saveArtist(data, file);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @GetMapping("/channel/{artist}/{type}")
    public ResponseEntity<Object> artistChannel(@PathVariable Long artist,@PathVariable String type, @AuthenticationPrincipal UserDetails userDetails){
        Map<String, Object> map = artService.artistChannel(artist, type, userDetails);
    
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));    
    }
    @GetMapping("/detail/{artist}")
    public ResponseEntity<Object> artistDetailPage(@PathVariable Long artist){
        Map<String, Object> map = artService.artistDetailPage(artist);
        
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));    
    }
}
