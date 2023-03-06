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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import melonproject.melon.service.SearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("search")
public class SearchController {
    private final SearchService sService;
    
    @GetMapping("/total/{type}")
    public ResponseEntity<Object> totalKeyword(@RequestParam String keyword, @AuthenticationPrincipal UserDetails userDetails, @PathVariable String type){
        Map<String, Object> map = sService.searchTotal(keyword, userDetails, type);

        return new ResponseEntity<>(map,(HttpStatus)map.get("code"));
    }
    @GetMapping("songName/{type}")
    public ResponseEntity<Object> songNameSearch(@RequestParam String keyword,  
        @PageableDefault(size=2, sort="siRegDt",direction = Sort.Direction.ASC) @Nullable Pageable page,
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable String type
    ){
        Map<String, Object> map = sService.searchSongName(keyword, page, userDetails, type);
        
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @GetMapping("artistName")
    public ResponseEntity<Object> artistNameSearch(@RequestParam String keyword,  @PageableDefault(size=10, sort="artDebut",direction = Sort.Direction.ASC) @Nullable Pageable page){
        Map<String, Object> map = sService.artistNameSearch(keyword, page);
        
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @GetMapping("songLyrics")
    public ResponseEntity<Object> songLyricsSearch(@RequestParam String keyword,  @PageableDefault(size=10, sort="siRegDt",direction = Sort.Direction.ASC) @Nullable Pageable page){
        Map<String, Object> map = sService.songLyricsSearch(keyword, page);
        
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @GetMapping("albumName")
    public ResponseEntity<Object> albumNameSearch(@RequestParam String keyword,  @PageableDefault(size=10, sort="albumRegDt",direction = Sort.Direction.ASC) @Nullable Pageable page){
        Map<String, Object> map = sService.albumSearch(keyword, page);
        
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }

}
