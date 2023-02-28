package melonproject.melon.vo.Member;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface MemberListenSong {
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm")
    LocalDateTime getDate();
    Long getCount();
}
