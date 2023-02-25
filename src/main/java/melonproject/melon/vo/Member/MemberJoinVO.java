package melonproject.melon.vo.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberJoinVO {
    private String id;
    private String pwd;
    private String name;
    private Integer age;
    private String phone;
    private String email;
    private LocalDate birth;
    private LocalDateTime regDt;
}
