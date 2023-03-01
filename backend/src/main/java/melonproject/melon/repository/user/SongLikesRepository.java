package melonproject.melon.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.SongLikesEntity;

public interface SongLikesRepository extends JpaRepository<SongLikesEntity, Long>{
    SongLikesEntity findBySongAndMember(SongInfoEntity song, MemberInfoEntity member);

    Integer countBySongAndMember(SongInfoEntity song, MemberInfoEntity member);
}
