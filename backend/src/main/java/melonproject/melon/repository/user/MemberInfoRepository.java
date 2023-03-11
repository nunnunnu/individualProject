package melonproject.melon.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.user.MemberInfoEntity;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Long>{

    MemberInfoEntity findByMiId(String username);

    MemberInfoEntity findByMiIdAndMiPwd(String id, String pwd);

    boolean existsByMiId(String id);
    
}
