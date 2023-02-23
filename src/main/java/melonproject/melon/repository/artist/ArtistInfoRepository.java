package melonproject.melon.repository.artist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.ArtistInfoEntity;

public interface ArtistInfoRepository extends JpaRepository<ArtistInfoEntity, Long>{

    ArtistInfoEntity findByArtUri(String uri);

    List<ArtistInfoEntity> findTop10ByArtNameContains(String keyword);

    
}
