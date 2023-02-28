package melonproject.melon.repository.artist.song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.song.SongArtistConnection;

public interface SongArtistConnectionRepository extends JpaRepository<SongArtistConnection, Long>{
    Page<SongArtistConnection> findByArtist(ArtistInfoEntity artist, Pageable page);
}
