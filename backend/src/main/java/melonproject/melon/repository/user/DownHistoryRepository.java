package melonproject.melon.repository.user;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.user.DownHistoryEntity;
import melonproject.melon.entity.user.MemberInfoEntity;

public interface DownHistoryRepository extends JpaRepository<DownHistoryEntity, Long>{
    Boolean existsBySongAndMember(SongInfoEntity song, MemberInfoEntity member);

    Integer countByMemberAndDhDateBetween(MemberInfoEntity member, LocalDateTime start, LocalDateTime end);

}
