package melonproject.melon.repository.info;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.info.AgencyInfoEntity;

public interface AgencyInfoRepository extends JpaRepository<AgencyInfoEntity, Long>{
    
}
