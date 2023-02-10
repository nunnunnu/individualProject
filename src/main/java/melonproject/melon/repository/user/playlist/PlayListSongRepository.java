package melonproject.melon.repository.user.playlist;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.playlist.PlayListSongEntity;

public interface PlayListSongRepository extends JpaRepository<PlayListSongEntity, Long>{
    
}
