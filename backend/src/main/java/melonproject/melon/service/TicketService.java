package melonproject.melon.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.song.SongInfoEntity;
import melonproject.melon.entity.info.TicketInfoEntity;
import melonproject.melon.entity.user.MemberInfoEntity;
import melonproject.melon.entity.user.TicketMemberEntity;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.NotFoundSongException;
import melonproject.melon.error.custom.NotFoundTicket;
import melonproject.melon.repository.artist.song.SongInfoRepository;
import melonproject.melon.repository.info.TicketInfoRepository;
import melonproject.melon.repository.user.DownHistoryRepository;
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
    private final SongInfoRepository sRepo;
    private final DownHistoryRepository dhRepo;

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
            map.put("message", "이미 이용중인 이용권이 있습니다.");
            map.put("code", HttpStatus.ACCEPTED);
            return map;
        }
        TicketInfoEntity ticket = tRepo.findById(seq).orElseThrow(()->new NotFoundTicket());

        TicketMemberEntity ticketMember = new TicketMemberEntity(null, ticket, member, LocalDateTime.now());
        tmRepo.save(ticketMember);

        map.put("status", true);
        map.put("message", "이용권 구매 성공");
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
    public Map<String, Object> downCheck(UserDetails userDetails, Long seq){
        MemberInfoEntity member = mRepo.findByMiId(userDetails.getUsername());
        if(member==null){
            throw new MemberNotFound();
        }
        SongInfoEntity song = sRepo.findById(seq).orElseThrow(()-> new NotFoundSongException());

        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1);     
        LocalDateTime end = start.withDayOfMonth(start.getMonth().length(start.toLocalDate().isLeapYear()));
        TicketMemberEntity ticketMember = tmRepo.findByMemberAndTmRegDtBetween(member, start, end);
        Map<String, Object> map = new LinkedHashMap<>();
        if(ticketMember==null){
            map.put("status", false);
            map.put("message", "이용중인 이용권이 없습니다.");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }else{
            LocalDateTime regDt = ticketMember.getTmRegDt();
            TicketInfoEntity ticket = ticketMember.getTicket();
            if(dhRepo.existsBySongAndMember(song, member)){
                map.put("status", true);
                map.put("message", "이미 다운로드한 음원입니다. 재다운로드하시겠습니까? 재다운로드하셔도 이용권에서 차감되지않습니다.");
                map.put("code", HttpStatus.OK);
                return map;
            }
            if(dhRepo.countByMemberAndDhDateBetween(member, regDt, regDt.plusDays(ticket.getTiTerm()))>=ticket.getTiDown()){
                map.put("status", false);
                map.put("message", "보유중인 이용권이 다운로드가능한 이용권이 아니거나 모든 횟수를 소진하셨습니다.");
                map.put("code", HttpStatus.BAD_REQUEST);
                return map;
            }
        }
        map.put("status", true);
        map.put("message", "정말 다운로드하시겠습니까? 다운로드시 이용권의 다운로드 횟수에서 차감됩니다.");
        map.put("code", HttpStatus.OK);
        return map;
    }
}
