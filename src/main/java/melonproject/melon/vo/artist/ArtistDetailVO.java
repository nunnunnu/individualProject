package melonproject.melon.vo.artist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.artist.ArtistGroupInfoEntity;
import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.ArtistSoloInfoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDetailVO {
    private String introduce;
    private LocalDate debut;
    private String type;
    private String agency;
    private List<ArtistListVO> artists = new ArrayList<>();
    private String country;
    private LocalDate birth;
    private String constellation;
    private LocalDate split;
    private String realname;

    public ArtistDetailVO(ArtistSoloInfoEntity entity){
        this.introduce = entity.getArtIntroduce();
        this.debut = entity.getArtDebut();
        this.type = entity.isType(entity);
        if(entity.getAgency()!=null){
            this.agency = entity.getAgency().getAiName();
        }
        this.country = entity.getArtCountry();
        this.birth = entity.getAsiBirth();
        this.constellation = entity.getAsiConstellation();
        this.realname = entity.getAsiRealName();
    }
    public ArtistDetailVO(ArtistGroupInfoEntity entity){
        this.introduce = entity.getArtIntroduce();
        this.debut = entity.getArtDebut();
        this.type = entity.isType(entity);
        if(entity.getAgency()!=null){
            this.agency = entity.getAgency().getAiName();
        }
        this.country = entity.getArtCountry();
        this.split = entity.getAgiSplit();
    }

    public void addArtists(ArtistInfoEntity entity){
        this.artists.add(new ArtistListVO(entity));
    }
    
}
