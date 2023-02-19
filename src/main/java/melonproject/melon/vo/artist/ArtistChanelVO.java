package melonproject.melon.vo.artist;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.ArtistGroupInfoEntity;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.ArtistSoloInfoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistChanelVO {
    private String name;
    private String type;
    private String agency;
    private Integer fan;
    private List<ArtistInfoVO> artists = new ArrayList<>();

    public ArtistChanelVO(ArtistInfoEntity entity){
        this.name = entity.getArtName();
        this.type = entity.isType(entity);
        if(entity.getAgency()!=null){
            this.agency = entity.getAgency().getAiName();
        }
        this.fan = entity.getFanList().size();
    }
    public void soloSetting(ArtistSoloInfoEntity solo){
        artists.add(new ArtistInfoVO(solo));
    }
    public void groupSetting(ArtistGroupInfoEntity solo){
        artists.add(new ArtistInfoVO(solo));
    }
}  