package melonproject.melon.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/song/{uri}/{member}/{song}")
    public ResponseEntity<Resource> getSong ( @PathVariable String uri, @PathVariable Long song,
            HttpServletRequest request, @PathVariable Long member ) throws Exception {
                System.out.println("???");
            songService.listenMusicList(member, song);

        return fService.getSong(uri, request);
    }
}
