package melonproject.melon.repository.artist.song;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.artist.song.SongArtistConnection;

public interface SongArtistConnectionRepository extends JpaRepository<SongArtistConnection, Long>{
    // @EntityGraph(attributePaths = {"artist","song"})
    @Query("select sac from SongArtistConnection sac join fetch sac.artist a join fetch sac.song s where sac.artist=:aritst order by s.siRegDt DESC, s.siOrder")
    List<SongArtistConnection> findByArtist(@Param("aritst") ArtistInfoEntity artist);
}
