package melonproject.melon.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.HistoryPlayEntity;

public interface HistoryPlayRepository extends JpaRepository<HistoryPlayEntity, Long>{
    
}
