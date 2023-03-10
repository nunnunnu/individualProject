package melonproject.melon.repository.user.playlist;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.playlist.PlayListInfoEntity;

public interface PlayListInfoRepository extends JpaRepository<PlayListInfoEntity, Long>{
    @EntityGraph(attributePaths = {"songs"})
    List<PlayListInfoEntity> findByMemberOrderByCreatedDate(MemberInfoEntity member);
    
    @EntityGraph(attributePaths = {"songs"})
    PlayListInfoEntity findByPlayiSeqAndMember(Long seq, MemberInfoEntity member);
    
}
