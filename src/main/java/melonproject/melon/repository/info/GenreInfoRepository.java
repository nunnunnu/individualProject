package melonproject.melon.repository.info;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.info.GenreInfoEntity;

public interface GenreInfoRepository extends JpaRepository<GenreInfoEntity, Long>{
    
}
