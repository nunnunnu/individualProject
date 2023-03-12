package melonproject.melon.repository.artist.album;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.album.AlbumCommentEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;

public interface AlbumCommentRepository extends JpaRepository<AlbumCommentEntity, Long>{

    AlbumCommentEntity findByAlbumcUri(String uri);

    Page<AlbumCommentEntity> findByAlbumAndParentIsNull(AlbumInfoEntity album, Pageable page);

    @EntityGraph(attributePaths={"member"})
    List<AlbumCommentEntity> findByParent(AlbumCommentEntity comment);
    
}
