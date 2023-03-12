package melonproject.melon.vo.comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.album.AlbumCommentEntity;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentListVO {
    private String name;
    private String content;
    private String uri;
    private LocalDateTime create;
    private LocalDateTime update;
    private List<ChildCommentListVO> child = new ArrayList<>();

    public CommentListVO(AlbumCommentEntity entity, List<AlbumCommentEntity> childlist){
        this.name = entity.getMember().getMiNickName();
        this.content = entity.getAlbumcCommnet();
        this.uri = entity.getAlbumcFile();
        this.create = entity.getCreatedDate();
        this.update = entity.getUpdatedDate();
        child.addAll(childlist.stream().map(c->new ChildCommentListVO(c)).toList());
    }

}
