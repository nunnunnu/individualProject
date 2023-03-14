package melonproject.melon.repository.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.TicketMemberEntity;

public interface TicketMemberRepository extends JpaRepository<TicketMemberEntity, Long>{
    @EntityGraph(attributePaths = {"ticket"})
    TicketMemberEntity findByMemberAndTmRegDtBetween(MemberInfoEntity member, LocalDateTime start, LocalDateTime end);

    Boolean existsByMemberAndTmRegDtBetween(MemberInfoEntity member, LocalDateTime start, LocalDateTime end);
}
