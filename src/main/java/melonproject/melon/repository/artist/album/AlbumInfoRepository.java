package melonproject.melon.repository.artist.album;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.album.AlbumInfoEntity;

public interface AlbumInfoRepository extends JpaRepository<AlbumInfoEntity, Long> {
    
}
