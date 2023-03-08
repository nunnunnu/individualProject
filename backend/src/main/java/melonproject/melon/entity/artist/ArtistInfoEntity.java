package melonproject.melon.entity.artist;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import melonproject.melon.entity.info.AgencyInfoEntity;
import melonproject.melon.entity.user.ArtistFanEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="artist_info")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="art_dtype")//기본값이 DTYPE
@SuperBuilder
public class ArtistInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="art_seq") private Long artSeq;
    @Column(name="art_name") private String artName;
    @Column(name="art_debut") private LocalDate artDebut;
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore @JoinColumn(name="art_ai_seq") private AgencyInfoEntity agency;
    @Column(name="art_country") private String artCountry;
    @Column(name="art_file") private String artFile;
    @Column(name="art_uri") private String artUri;
    // @OneToMany(mappedBy = "group")
    // private Set<ArtistConnectionEntity> SoloList = new HashSet<>();
    @OneToMany(mappedBy = "artist")
    private Set<ArtistFanEntity> fanList = new HashSet<>();
    @Column(name="art_introduce") private String artIntroduce;

    public String isType(ArtistInfoEntity artist){
        if(artist instanceof ArtistSoloInfoEntity){
            return "솔로";
        }else if(artist instanceof ArtistGroupInfoEntity){
            return "그룹";
        }else{
            return null;
        }
    }

}

