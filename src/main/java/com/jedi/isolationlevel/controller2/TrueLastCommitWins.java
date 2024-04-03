package com.jedi.isolationlevel.controller2;

import com.jedi.isolationlevel.isolation.Ticket;
import com.jedi.isolationlevel.service.LostUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/last-commit-wins")
@RequiredArgsConstructor
public class TrueLastCommitWins {
    private final LostUpdateService lostUpdateService;

    @PostMapping("/sell-ticket/{id}")
    public void SellTicket(@PathVariable Long id,
                           @RequestParam Integer sellCount,
                           @RequestParam String isolation,
                           @RequestParam Boolean isT1) {
        switch (Isolation.valueOf(isolation)) {
            case READ_UNCOMMITTED:
                lostUpdateService.sellTicketREAD_UNCOMMITTED(id, sellCount, isT1);
                return;
            case READ_COMMITTED:
                lostUpdateService.sellTicketREAD_COMMITTED(id, sellCount, isT1);
                return;
            case REPEATABLE_READ:
                lostUpdateService.sellTicketREPEATABLE_READ(id, sellCount, isT1);
                return;
            case SERIALIZABLE:
                lostUpdateService.sellTicketSERIALIZABLE(id, sellCount, isT1);
                return;
            case DEFAULT:
            default:
                lostUpdateService.sellTicketDEFAULT(id, sellCount, isT1);
        }
    }

    @GetMapping("/ticket/{id}")
    public Ticket getRemainingTicket(@PathVariable Long id) {
        return lostUpdateService.getRemainingTicket(id);
    }
}
