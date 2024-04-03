package com.jedi.isolationlevel.controller1;

import com.jedi.isolationlevel.isolation.GameTask;
import com.jedi.isolationlevel.service.PhantomReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/phantom-read")
@RequiredArgsConstructor
public class PhantomReadController {
    private final PhantomReadService phantomReadService;

    @GetMapping("/audit")
    public List<GameTask> auditGameTask(@RequestParam Integer auditScore,
                                        @RequestParam String isolation) {
        switch (Isolation.valueOf(isolation)) {
            case READ_UNCOMMITTED:
                return phantomReadService.auditGameTaskREAD_UNCOMMITTED(auditScore);
            case READ_COMMITTED:
                return phantomReadService.auditGameTaskREAD_COMMITTED(auditScore);
            case REPEATABLE_READ:
                return phantomReadService.auditGameTaskREPEATABLE_READ(auditScore);
            case SERIALIZABLE:
                return phantomReadService.auditGameTaskSERIALIZABLE(auditScore);
            case DEFAULT:
            default:
                return phantomReadService.auditGameTaskDEFAULT(auditScore);
        }
    }

    @PostMapping("/create")
    public void create(@RequestParam String name,
                       @RequestParam Integer score) {
        phantomReadService.createGameTask(name, score);
    }
}
