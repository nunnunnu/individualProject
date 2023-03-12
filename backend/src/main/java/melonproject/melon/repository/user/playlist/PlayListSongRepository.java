package melonproject.melon.repository.user.playlist;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import melonproject.melon.entity.user.playlist.PlayListInfoEntity;
import melonproject.melon.entity.user.playlist.PlayListSongEntity;

public interface PlayListSongRepository extends JpaRepository<PlayListSongEntity, Long>{
    @EntityGraph( attributePaths = {"song"})
    List<PlayListSongEntity> findByPlayOrderByPsOrder(PlayListInfoEntity play);
    
    List<PlayListSongEntity> findByPlay(PlayListInfoEntity play);

    PlayListSongEntity findByPlayAndPsOrder(PlayListInfoEntity play,Integer order);

    @Transactional
    @Modifying
    @Query("update PlayListSongEntity p set p.psOrder=p.psOrder-1 where p.play=:play and p.psOrder > :order")
    void playlistOrderSet(@Param("play") PlayListInfoEntity play, @Param("order") Integer order);
}
