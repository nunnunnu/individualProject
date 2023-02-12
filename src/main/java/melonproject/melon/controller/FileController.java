package melonproject.melon.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import melonproject.melon.service.FileService;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fService;

    @GetMapping("/image/{type}/{uri}")
    public ResponseEntity<Resource> getImage ( @PathVariable String uri, 
            @PathVariable String type , HttpServletRequest request ) throws Exception { 
                return fService.getImage(uri, type, request);
    }
}
