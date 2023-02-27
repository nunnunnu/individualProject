package melonproject.melon.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.user.HistoryPlayEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.vo.Member.MemberListenSong;

public interface HistoryPlayRepository extends JpaRepository<HistoryPlayEntity, Long>{
    // Long countBySongAndMember(SongInfoEntity song, MemberInfoEntity member);
    @Query("select count(h) as count, min(h.hpStart) as date from HistoryPlayEntity h where h.member =:member and h.song=:song")
    MemberListenSong listenCount(@Param("member") MemberInfoEntity member, @Param("song") SongInfoEntity song);
}
