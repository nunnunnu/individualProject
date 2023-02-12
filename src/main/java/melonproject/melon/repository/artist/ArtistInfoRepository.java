package melonproject.melon.repository.artist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import melonproject.melon.entity.artist.ArtistInfoEntity;

public interface ArtistInfoRepository extends JpaRepository<ArtistInfoEntity, Long>{

    ArtistInfoEntity findByArtUri(String uri);

    
}
