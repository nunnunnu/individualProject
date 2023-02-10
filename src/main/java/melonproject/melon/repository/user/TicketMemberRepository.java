package melonproject.melon.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.TicketMemberEntity;

public interface TicketMemberRepository extends JpaRepository<TicketMemberEntity, Long>{
    
}
