package melonproject.melon.repository.user.custom;

import java.util.List;

import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.vo.Member.chartVO.UserGenreVO;

public interface HistoryPlayRepositoryCustom {
    List<UserGenreVO> countListenGenre(MemberInfoEntity member);
}
