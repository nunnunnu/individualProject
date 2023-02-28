package melonproject.melon.repository.info;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import melonproject.melon.entity.info.GenreInfoEntity;

public interface GenreInfoRepository extends JpaRepository<GenreInfoEntity, Long>{
    @Query("select g from GenreInfoEntity g where g.giSeq in :seqs")
    List<GenreInfoEntity> findIdList(@Param("seqs") Long[] seq);
    
}
