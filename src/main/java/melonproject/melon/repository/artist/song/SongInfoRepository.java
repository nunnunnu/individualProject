package melonproject.melon.repository.artist.song;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SongInfoEntity;

public interface SongInfoRepository extends JpaRepository<SongInfoEntity, Long>{

    List<SongInfoEntity> findByAlbum(AlbumInfoEntity album);

    SongInfoEntity findTop1ByAlbumAndSiTitle(AlbumInfoEntity album, Boolean title);

    
}
