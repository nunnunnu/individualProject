package melonproject.melon.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import melonproject.melon.service.FileService;
import melonproject.melon.service.SongService;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fService;
    private final SongService songService;

    @GetMapping("/image/{type}/{uri}")
    public ResponseEntity<Resource> getImage ( @PathVariable String uri, 
            @PathVariable String type , HttpServletRequest request ) throws Exception {
        
        return fService.getImage(uri, type, request);
    }
    @GetMapping("/songfile/{uri}/{song}")
    public ResponseEntity<Resource> getSong ( @PathVariable String uri, @PathVariable Long song, @AuthenticationPrincipal UserDetails userDetails,
            HttpServletRequest request ) throws Exception {
            songService.listenMusicList(userDetails, song);

        return fService.getSong(uri, request);
    }
}
