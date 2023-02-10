package melonproject.melon.repository.artist.song;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.song.SongCreatorEntity;

public interface SongCreatorRepository extends JpaRepository<SongCreatorEntity, Long>{
    
}
