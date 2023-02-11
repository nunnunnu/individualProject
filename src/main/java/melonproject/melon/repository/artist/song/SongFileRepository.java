package melonproject.melon.repository.artist.song;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.song.SongFileEntity;

public interface SongFileRepository extends JpaRepository<SongFileEntity, Long>{

    SongFileEntity findBySfUri(String uri);
    
}
