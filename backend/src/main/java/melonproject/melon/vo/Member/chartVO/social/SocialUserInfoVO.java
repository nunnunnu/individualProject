package melonproject.melon.vo.Member.chartVO.social;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import melonproject.melon.security.vo.TokenVO;

@Getter
@Setter
@NoArgsConstructor
public class SocialUserInfoVO {
    private Long seq;
    private String id;
    private String nickname;
    private TokenVO token;

    public SocialUserInfoVO(Long seq, String nickname, String id)    {
        this.id = id;
        this.nickname = nickname;
        this.seq = seq;
    }
}
