package melonproject.melon.repository.artist;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.ArtistConnectionEntity;

public interface ArtistConnectionRepository extends JpaRepository<ArtistConnectionEntity, Long>{
    
}
