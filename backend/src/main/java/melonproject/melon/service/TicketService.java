package melonproject.melon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import melonproject.melon.repository.info.TicketInfoRepository;
import melonproject.melon.vo.ticket.TicketInfoVO;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketInfoRepository tRepo;

    public List<TicketInfoVO> ticketAll(){
        List<TicketInfoVO> result = tRepo.findAll().stream().map((t)->new TicketInfoVO(t)).toList();

        return result;
    }
}
