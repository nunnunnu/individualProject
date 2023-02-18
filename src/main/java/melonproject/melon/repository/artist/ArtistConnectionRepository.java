package melonproject.melon.repository.artist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.ArtistConnectionEntity;
import melonproject.melon.entity.artist.ArtistInfoEntity;

public interface ArtistConnectionRepository extends JpaRepository<ArtistConnectionEntity, Long>{
    List<ArtistConnectionEntity> findByGroup(ArtistInfoEntity artist);

    List<ArtistConnectionEntity> findBySolo(ArtistInfoEntity artist);
}
