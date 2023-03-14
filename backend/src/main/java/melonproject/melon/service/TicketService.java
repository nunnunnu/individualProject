package melonproject.melon.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.info.TicketInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.TicketMemberEntity;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.NotFoundTicket;
import melonproject.melon.repository.info.TicketInfoRepository;
import melonproject.melon.repository.user.HistoryPlayRepository;
import melonproject.melon.repository.user.MemberInfoRepository;
import melonproject.melon.repository.user.TicketMemberRepository;
import melonproject.melon.vo.ticket.TicketInfoVO;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketInfoRepository tRepo;
    private final MemberInfoRepository mRepo;
    private final TicketMemberRepository tmRepo;
    private final HistoryPlayRepository hpRepo;

    public List<TicketInfoVO> ticketAll(){
        List<TicketInfoVO> result = tRepo.findAll().stream().map((t)->new TicketInfoVO(t)).toList();

        return result;
    }
    public Map<String, Object> buyTicket(UserDetails userDetails, Long seq){
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        if(tmRepo.existsByMemberAndTmRegDtBetween(member, LocalDateTime.now().minusDays(30), LocalDateTime.now())){
            map.put("status", false);
            map.put("message", "이미 구매한 이용권입니다.");
            map.put("code", HttpStatus.ACCEPTED);
            return map;
        }
        TicketInfoEntity ticket = tRepo.findById(seq).orElseThrow(()->new NotFoundTicket());

        TicketMemberEntity ticketMember = new TicketMemberEntity(null, ticket, member, LocalDateTime.now());
        tmRepo.save(ticketMember);

        map.put("status", false);
        map.put("message", "이미 이용중인 이용권이 있습니다.");
        map.put("code", HttpStatus.OK);
        return map;
    }
    public Map<String, Object> myTicketCheck(UserDetails userDetails){
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1);     
        LocalDateTime end = start.withDayOfMonth(start.getMonth().length(start.toLocalDate().isLeapYear()));
        TicketMemberEntity ticketMember = tmRepo.findByMemberAndTmRegDtBetween(member, start, end);

        if(ticketMember==null){
            map.put("status", false);
            map.put("message", "이용중인 이용권이 없습니다. 먼저 이용권을 구매해주세요.");
            map.put("code", HttpStatus.BAD_REQUEST);
        }else{
            LocalDateTime regDt = ticketMember.getTmRegDt();
            TicketInfoEntity ticket = ticketMember.getTicket();
            if(hpRepo.countByMemberAndHpStartBetween(member, regDt, regDt.plusDays(ticket.getTiTerm()))>=ticket.getTiStreaming()){
                map.put("status", false);
                map.put("message", "스트리밍 가능 횟수를 초과하였습니다. 무제한 이용권을 사용중이시라면 관리자에게 문의해주세요.");
                map.put("code", HttpStatus.BAD_REQUEST);
            }else{
                map.put("status", true);
                map.put("message", "사용가능");
                map.put("code", HttpStatus.OK);
            }
        }
        return map;
    }
}
