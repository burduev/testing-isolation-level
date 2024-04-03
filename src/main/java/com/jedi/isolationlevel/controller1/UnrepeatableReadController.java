package com.jedi.isolationlevel.controller1;

import com.jedi.isolationlevel.isolation.Inventory;
import com.jedi.isolationlevel.service.UnrepeatableReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/unrepeatable-read")
@RequiredArgsConstructor
public class UnrepeatableReadController {
    private final UnrepeatableReadService unrepeatableReadService;

    @GetMapping("/acquire/{id}")
    public Inventory acquireInventory(@PathVariable Long id, @RequestParam String isolation) {
        switch (Isolation.valueOf(isolation)) {
            case READ_UNCOMMITTED:
                return unrepeatableReadService.acquireREAD_UNCOMMITTED(id);
            case READ_COMMITTED:
                return unrepeatableReadService.acquireREAD_COMMITTED(id);
            case REPEATABLE_READ:
                return unrepeatableReadService.acquireREPEATABLE_READ(id);
            case SERIALIZABLE:
                return unrepeatableReadService.acquireSERIALIZABLE(id);
            case DEFAULT:
            default:
                return unrepeatableReadService.acquireDEFAULT(id);
        }
    }

    @PostMapping("/purchase/{id}")
    public void purchaseInventory(@PathVariable Long id, @RequestParam Integer quantity) {
        unrepeatableReadService.purchase(id, quantity);
    }
}
