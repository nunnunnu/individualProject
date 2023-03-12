package melonproject.melon.vo.album;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentInputVO {
    @NotNull(message="앨범 번호 누락")
    private Long album;
    @NotBlank(message="댓글 내용 누락")
    private String comment;
    private Long parentSeq;
}
