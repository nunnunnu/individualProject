package melonproject.melon.repository.artist;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.ArtistInfoEntity;

public interface ArtistInfoRepository extends JpaRepository<ArtistInfoEntity, Long>{
    
}
