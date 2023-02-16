package melonproject.melon.vo.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumInfoVO {
    private Long seq;
    private String name;
    private String uri;
}
