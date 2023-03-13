package melonproject.melon.vo.comment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumCommentEntity;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildCommentListVO {
    private Long seq;
    private String name;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime create;
    private LocalDateTime update;

    public ChildCommentListVO(AlbumCommentEntity entity){
        this.seq = entity.getAlbumcSeq();
        this.name = entity.getMember().getMiNickName();
        this.content = entity.getAlbumcCommnet();
        this.create = entity.getCreatedDate();
        this.update = entity.getUpdatedDate();
    }
}
