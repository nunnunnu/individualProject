package melonproject.melon.repository.user;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.TicketMemberEntity;

public interface TicketMemberRepository extends JpaRepository<TicketMemberEntity, Long>{
    TicketMemberEntity findByMemberAndTmRegDtBetween(MemberInfoEntity member, LocalDate start, LocalDate end);
}
