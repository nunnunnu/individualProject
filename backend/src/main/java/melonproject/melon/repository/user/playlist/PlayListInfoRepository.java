package melonproject.melon.repository.user.playlist;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.playlist.PlayListInfoEntity;

public interface PlayListInfoRepository extends JpaRepository<PlayListInfoEntity, Long>{
    
}
