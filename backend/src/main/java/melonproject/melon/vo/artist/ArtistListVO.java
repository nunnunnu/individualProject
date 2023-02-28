package melonproject.melon.vo.artist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.ArtistInfoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistListVO {
    private Long seq;
    private String name;
    private String uri;

    public ArtistListVO(ArtistInfoEntity artist){
        this.seq = artist.getArtSeq();
        this.name = artist.getArtName();
        this.uri = artist.getArtUri();
    }
}
