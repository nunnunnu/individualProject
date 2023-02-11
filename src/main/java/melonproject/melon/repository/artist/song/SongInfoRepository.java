package melonproject.melon.repository.artist.song;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.song.SongInfoEntity;

public interface SongInfoRepository extends JpaRepository<SongInfoEntity, Long>{

    
}
