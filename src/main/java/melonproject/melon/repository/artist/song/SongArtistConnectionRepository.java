package melonproject.melon.repository.artist.song;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.song.SongArtistConnection;

public interface SongArtistConnectionRepository extends JpaRepository<SongArtistConnection, Long>{
    List<SongArtistConnection> findByArtist(ArtistInfoEntity artist);
}
