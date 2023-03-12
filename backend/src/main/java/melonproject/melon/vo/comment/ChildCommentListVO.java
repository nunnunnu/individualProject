package melonproject.melon.vo.comment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumCommentEntity;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildCommentListVO {
    private String name;
    private String content;
    private LocalDateTime create;
    private LocalDateTime update;

    public ChildCommentListVO(AlbumCommentEntity entity){
        this.name = entity.getMember().getMiName();
        this.content = entity.getAlbumcCommnet();
        this.create = entity.getCreatedDate();
        this.update = entity.getUpdatedDate();
    }
}
