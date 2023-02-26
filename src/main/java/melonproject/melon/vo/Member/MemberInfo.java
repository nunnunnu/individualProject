package melonproject.melon.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import melonproject.melon.entity.info.TicketInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.TicketMemberEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfo {
    private String nickname;
    private String ticket;

    public MemberInfo(MemberInfoEntity member, TicketMemberEntity ticket){
        this.nickname = member.getMiNickName();
        if(ticket!=null){
            this.ticket = ticket.getTicket().getName();
        }
    }
}
