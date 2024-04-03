package com.jedi.isolationlevel.controller2;

import com.jedi.isolationlevel.isolation.Account2;
import com.jedi.isolationlevel.service.SeedService;
import com.jedi.isolationlevel.service.TrueLostUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lost-update-true")
@RequiredArgsConstructor
public class TrueLostUpdateController {
    private final TrueLostUpdateService trueLostUpdateService;
    private final SeedService seedService;

    @PostMapping("/deposit/{id}")
    public void deposit(@PathVariable Long id, @RequestParam Integer amount) {
        trueLostUpdateService.deposit(id, amount);
    }

    @PostMapping("/withdraw/{id}")
    public void withdraw(@PathVariable Long id, @RequestParam Integer amount, @RequestParam String isolation) {
        switch (Isolation.valueOf(isolation)) {
            case READ_UNCOMMITTED:
                trueLostUpdateService.withdrawReadUncommitted(id, amount);
                return;
            case READ_COMMITTED:
                trueLostUpdateService.withdrawReadCommitted(id, amount);
                return;
            case REPEATABLE_READ:
                trueLostUpdateService.withdrawRepeatableRead(id, amount);
                return;
            case SERIALIZABLE:
                trueLostUpdateService.withdrawSerializable(id, amount);
                return;
            case DEFAULT:
            default:
                trueLostUpdateService.withdrawDefault(id, amount);
        }
    }

    @PostMapping("/withdraw/{id}/repeatable-read")
    public void withdraw(@PathVariable Long id, @RequestParam Integer amount) {
        trueLostUpdateService.withdrawRepeatableRead(id, amount);
    }

    @GetMapping("account/{id}")
    public Account2 readResult(@PathVariable Long id) {
        Account2 account = trueLostUpdateService.getAccount(id);
        return account;
    }
}
