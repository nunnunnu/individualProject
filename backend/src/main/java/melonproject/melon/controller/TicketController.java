package melonproject.melon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import melonproject.melon.service.TicketService;
import melonproject.melon.vo.ticket.TicketInfoVO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService tService;

    @GetMapping("")
    public ResponseEntity<List<TicketInfoVO>> findTicketAll(){
        List<TicketInfoVO> result = tService.ticketAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
