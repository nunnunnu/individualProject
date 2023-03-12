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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import melonproject.melon.service.CommentService;
import melonproject.melon.vo.comment.CommentInputVO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService cService;
    @PutMapping("")
    public ResponseEntity<Object> commentAdd(
            @AuthenticationPrincipal UserDetails userDetails, 
            @Valid CommentInputVO data, 
            BindingResult bindingResult,
            @Nullable MultipartFile file
        ){
        Map<String, Object> map = cService.albumCommentAdd(userDetails, data, bindingResult, file);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
