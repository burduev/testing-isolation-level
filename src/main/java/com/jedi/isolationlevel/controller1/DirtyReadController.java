package com.jedi.isolationlevel.controller1;

import com.jedi.isolationlevel.isolation.Account;
import com.jedi.isolationlevel.service.DirtyReadService;
import com.jedi.isolationlevel.service.SeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dirty-read")
@RequiredArgsConstructor
public class DirtyReadController {
    private final DirtyReadService dirtyReadService;
    private final SeedService seedService;

    @PostMapping("/deposit/{id}")
    public void deposit(@PathVariable Long id, @RequestParam Integer amount) {
        dirtyReadService.deposit(id, amount);
    }

    @PostMapping("/withdraw/{id}")
    public void withdraw(@PathVariable Long id, @RequestParam Integer amount, @RequestParam String isolation) {
        switch (Isolation.valueOf(isolation)) {
            case READ_UNCOMMITTED:
                dirtyReadService.withdrawReadUncommitted(id, amount);
                return;
            case READ_COMMITTED:
                dirtyReadService.withdrawReadCommitted(id, amount);
                return;
            case REPEATABLE_READ:
                dirtyReadService.withdrawRepeatableRead(id, amount);
                return;
            case SERIALIZABLE:
                dirtyReadService.withdrawSerializable(id, amount);
                return;
            case DEFAULT:
            default:
                dirtyReadService.withdrawDefault(id, amount);
        }
    }

    @PostMapping("/withdraw/{id}/repeatable-read")
    public void withdraw(@PathVariable Long id, @RequestParam Integer amount) {
        dirtyReadService.withdrawRepeatableRead(id, amount);
    }

    @GetMapping("account/{id}")
    public Account readResult(@PathVariable Long id) {
        Account account = dirtyReadService.getAccount(id);
        return account;
    }
}
