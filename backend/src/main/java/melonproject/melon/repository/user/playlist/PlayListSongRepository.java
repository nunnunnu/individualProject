package melonproject.melon.repository.user.playlist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.playlist.PlayListInfoEntity;
import melonproject.melon.entity.user.playlist.PlayListSongEntity;

public interface PlayListSongRepository extends JpaRepository<PlayListSongEntity, Long>{
    List<PlayListSongEntity> findByPlayOrderByPsOrder(PlayListInfoEntity play);
    
    List<PlayListSongEntity> findByPlay(PlayListInfoEntity play);
}
