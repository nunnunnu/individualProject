package melonproject.melon.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("/total")
    public ResponseEntity<Object> totalKeyword(@RequestParam String keyword){
        Map<String, Object> map = sService.searchTotal(keyword);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
