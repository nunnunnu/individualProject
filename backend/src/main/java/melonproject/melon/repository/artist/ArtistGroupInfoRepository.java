package melonproject.melon.repository.artist;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.ArtistGroupInfoEntity;

public interface ArtistGroupInfoRepository extends JpaRepository<ArtistGroupInfoEntity, Long>{
    
}
