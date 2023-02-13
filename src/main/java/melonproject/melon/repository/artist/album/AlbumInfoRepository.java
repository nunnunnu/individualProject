package melonproject.melon.repository.artist.album;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.album.AlbumInfoEntity;

public interface AlbumInfoRepository extends JpaRepository<AlbumInfoEntity, Long> {

    AlbumInfoEntity findByAlbumUri(String uri);
    
    @EntityGraph(attributePaths = {"publisher","agency","artist","songList"})
    Page<AlbumInfoEntity> findByalbumRegDtLessThanEqual(Pageable page, LocalDate date);
    
}
