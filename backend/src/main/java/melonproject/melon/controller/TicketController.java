package melonproject.melon.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PutMapping("/{seq}")
    public ResponseEntity<Object> buyTicket(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long seq){
        Map<String, Object> map = tService.buyTicket(userDetails, seq);

        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
    @GetMapping("/check")
    public ResponseEntity<Object> checkTicket(@AuthenticationPrincipal UserDetails userDetails){
        Map<String, Object> map = tService.myTicketCheck(userDetails);
        
        return new ResponseEntity<>(map, (HttpStatus)map.get("code"));
    }
}
