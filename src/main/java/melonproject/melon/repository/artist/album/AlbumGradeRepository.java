package melonproject.melon.repository.artist.album;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.album.AlbumGradeEntity;

public interface AlbumGradeRepository extends JpaRepository<AlbumGradeEntity, Long>{
    
}
