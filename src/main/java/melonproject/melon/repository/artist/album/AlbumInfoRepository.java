package melonproject.melon.repository.artist.album;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import melonproject.melon.entity.artist.album.AlbumInfoEntity;

public interface AlbumInfoRepository extends JpaRepository<AlbumInfoEntity, Long> {

    AlbumInfoEntity findByAlbumUri(String uri);
    
    @EntityGraph(attributePaths = {"publisher","agency","artist","songList"})
    Page<AlbumInfoEntity> findByalbumRegDtLessThanEqual(Pageable page, LocalDate date);

    @Query("select a from AlbumInfoEntity a join fetch a.publisher join fetch a.agency join fetch a.artist join fetch a.songList join fetch a.genreList join left fetch a.commentList where a.albumSeq = :seq")
    AlbumInfoEntity findAllFetch(@Param("seq") Long albumSeq);
    
}
