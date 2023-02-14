package melonproject.melon.repository.artist.album;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.album.AlbumGradeEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;

public interface AlbumGradeRepository extends JpaRepository<AlbumGradeEntity, Long>{
    Long countByAlbumAndMember(AlbumInfoEntity album, MemberInfoEntity member);
}
