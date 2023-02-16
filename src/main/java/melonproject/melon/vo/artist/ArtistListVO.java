package melonproject.melon.vo.artist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistListVO {
    private Long seq;
    private String name;
    private String uri;
}
