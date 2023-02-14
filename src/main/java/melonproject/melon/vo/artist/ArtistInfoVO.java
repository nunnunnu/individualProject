package melonproject.melon.vo.artist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.ArtistInfoEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistInfoVO {
    private Long seq;
    private String name;

    public ArtistInfoVO(ArtistInfoEntity entity){
        this.seq = entity.getArtSeq();
        this.name = entity.getArtName();
    }
}
