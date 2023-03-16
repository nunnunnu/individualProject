package melonproject.melon.repository.artist.song;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.song.SongFileEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.artist.song.SoundQuality;

public interface SongFileRepository extends JpaRepository<SongFileEntity, Long>{

    SongFileEntity findBySfUri(String uri);

    SongFileEntity findBySongAndSfQuality(SongInfoEntity song, SoundQuality quality);

    List<SongFileEntity> findBySong(SongInfoEntity song);
    
}
