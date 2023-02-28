package melonproject.melon.repository.artist.album;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.album.AlbumGenreConnection;

public interface AlbumGenreConnectionRepository extends JpaRepository<AlbumGenreConnection, Long>{
    
}
