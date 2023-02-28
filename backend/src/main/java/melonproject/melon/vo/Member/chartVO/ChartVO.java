package melonproject.melon.vo.Member.chartVO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartVO {
    private List<String> labels = new ArrayList<>();
    private List<Long> data = new ArrayList<>();

    public ChartVO(List<UserGenreVO> vo){
        for(UserGenreVO v : vo){
            this.labels.add(v.getGenre());
            this.data.add(v.getCount());
        }
    }
}
