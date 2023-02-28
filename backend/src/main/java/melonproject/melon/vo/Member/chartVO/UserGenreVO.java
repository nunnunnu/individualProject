package melonproject.melon.vo.Member.chartVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGenreVO {
    private String genre;
    private Long count;
}
