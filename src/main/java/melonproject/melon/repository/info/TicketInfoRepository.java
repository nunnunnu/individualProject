package melonproject.melon.repository.info;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.info.TicketInfoEntity;

public interface TicketInfoRepository extends JpaRepository<TicketInfoEntity, Long>{
    
}
