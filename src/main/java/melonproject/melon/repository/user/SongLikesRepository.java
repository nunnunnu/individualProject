package melonproject.melon.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.SongLikesEntity;

public interface SongLikesRepository extends JpaRepository<SongLikesEntity, Long>{
    
}
