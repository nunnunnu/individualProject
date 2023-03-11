package melonproject.melon.vo.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberJoinVO {
    @NotBlank
    private String id;
    @NotBlank
    @Pattern(regexp="[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String pwd;
    private String name;
    // @NotNull(message="나이를 입력하지않으셨습니다.")
    @Min(value=14, message="14세 이상 가입가능합니다.")
    private Integer age;
    private String phone;
    @Email(message="이메일 형식이 아닙니다.")
    private String email;
    private LocalDate birth;
    private LocalDateTime regDt;
    @NotBlank(message = "닉네임을 입력하지않으셨습니다.")
    private String nickName;
}
