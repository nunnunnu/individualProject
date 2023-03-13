package melonproject.melon.vo.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.info.TicketInfoEntity;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketInfoVO {
    private Long seq;
    private String name;
    private String ex;
    private Integer price;

    public TicketInfoVO(TicketInfoEntity entity){
        this.seq = entity.getTiSeq();
        this.name = entity.getName();
        this.ex = entity.getTiEx();
        this.price = entity.getTiPrice();
    }
}
