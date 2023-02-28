package melonproject.melon.repository.artist.album;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.album.AlbumCommentEntity;

public interface AlbumCommentRepository extends JpaRepository<AlbumCommentEntity, Long>{

    AlbumCommentEntity findByAlbumcUri(String uri);
    
}
