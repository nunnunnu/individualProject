package melonproject.melon.repository.artist.song;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.song.SongArtistConnection;

public interface SongArtistConnectionRepository extends JpaRepository<SongArtistConnection, Long>{
    
}
