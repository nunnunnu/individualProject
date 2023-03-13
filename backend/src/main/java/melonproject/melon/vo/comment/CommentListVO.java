package melonproject.melon.vo.comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    private Long seq;
    private String name;
    private String content;
    private String uri;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime create;
    private LocalDateTime update;
    private List<ChildCommentListVO> child = new ArrayList<>();

    public CommentListVO(AlbumCommentEntity entity, List<AlbumCommentEntity> childlist){
        this.seq = entity.getAlbumcSeq();
        this.name = entity.getMember().getMiNickName();
        this.content = entity.getAlbumcCommnet();
        this.uri = entity.getAlbumcFile();
        this.create = entity.getCreatedDate();
        this.update = entity.getUpdatedDate();
        child.addAll(childlist.stream().map(c->new ChildCommentListVO(c)).toList());
    }

}
