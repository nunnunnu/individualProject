package melonproject.melon.repository.artist.album;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;

public interface AlbumInfoRepository extends JpaRepository<AlbumInfoEntity, Long> {

    AlbumInfoEntity findByAlbumUri(String uri);
    
    // @EntityGraph(attributePaths = {"publisher","agency","artist","songList"})
    Page<AlbumInfoEntity> findByalbumRegDtLessThanEqual(Pageable page, LocalDate date);

    @Query("select a from AlbumInfoEntity a join fetch a.publisher join fetch a.agency left join fetch a.artist left join fetch a.songList left join fetch a.genreList left join fetch a.commentList where a.albumSeq = :seq")
    AlbumInfoEntity findAllFetch(@Param("seq") Long albumSeq);

    Page<AlbumInfoEntity> findByArtist(ArtistInfoEntity artist, Pageable page);

    List<AlbumInfoEntity> findTop2ByOrderByAlbumRegDtDesc();

    List<AlbumInfoEntity> findTop10ByAlbumNameContains(String keyword);

    Page<AlbumInfoEntity> findByAlbumNameContains(String keyword, Pageable page);
    
}
