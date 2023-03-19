package melonproject.melon.repository.artist.song;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;

public interface SongInfoRepository extends JpaRepository<SongInfoEntity, Long>{

    List<SongInfoEntity> findByAlbum(AlbumInfoEntity album);

    SongInfoEntity findTop1ByAlbumAndSiTitle(AlbumInfoEntity album, Boolean title);

    Page<SongInfoEntity> findByOrderBySiRegDtDescSiSeqAsc(Pageable page);

    List<SongInfoEntity> findTop10BySiNameContains(String keyword);

    List<SongInfoEntity> findTop10BySiLyricsContains(String keyword);

    Page<SongInfoEntity> findBySiNameContains(String keyword, Pageable page);

    Page<SongInfoEntity> findBySiLyricsContains(String keyword, Pageable page);

    
}
