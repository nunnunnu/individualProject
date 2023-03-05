package melonproject.melon.vo.Member;

import org.springframework.data.redis.core.TimeToLive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfoVO {
    private Long memberSeq;
    private String refreshToken;

    @TimeToLive
    private Long refreshTokenExpirationTime;

    @Getter
    @Setter
    public static class Reissue {
        private String accessToken;

        private String refreshToken;
    }
}
