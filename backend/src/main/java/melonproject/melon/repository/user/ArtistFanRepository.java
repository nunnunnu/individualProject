package melonproject.melon.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import melonproject.melon.entity.artist.ArtistInfoEntity;
import melonproject.melon.entity.user.ArtistFanEntity;
import melonproject.melon.entity.user.MemberInfoEntity;

public interface ArtistFanRepository extends JpaRepository<ArtistFanEntity, Long>{
    boolean existsByArtistAndMember(ArtistInfoEntity artist, MemberInfoEntity member);

    ArtistFanEntity findByArtistAndMember(ArtistInfoEntity artist, MemberInfoEntity member);
}
