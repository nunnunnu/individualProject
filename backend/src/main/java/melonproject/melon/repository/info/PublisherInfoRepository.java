package melonproject.melon.repository.info;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.info.PublisherInfoEntity;

public interface PublisherInfoRepository extends JpaRepository<PublisherInfoEntity, Long>{
    
}
