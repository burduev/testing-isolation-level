package com.jedi.isolationlevel.service;

import com.jedi.isolationlevel.isolation.GameTask;
import com.jedi.isolationlevel.repository.GameTaskRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.TransactionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhantomReadService extends BaseService {
    private final GameTaskRepository gameTaskRepository;
    private final EntityManager entityManager;

    @Transactional(isolation = Isolation.DEFAULT)
    public List<GameTask> auditGameTaskDEFAULT(Integer auditScore) {
        return auditGameTask(auditScore);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<GameTask> auditGameTaskREAD_UNCOMMITTED(Integer auditScore) {
        return auditGameTask(auditScore);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<GameTask> auditGameTaskREAD_COMMITTED(Integer auditScore) {
        return auditGameTask(auditScore);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<GameTask> auditGameTaskREPEATABLE_READ(Integer auditScore) {
        return auditGameTask(auditScore);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<GameTask> auditGameTaskSERIALIZABLE(Integer auditScore) {
        return auditGameTask(auditScore);
    }

    public List<GameTask> auditGameTask(Integer auditScore) {
        List<GameTask> auditGameTasks = gameTaskRepository.findGameTasksByScoreGreaterThan(auditScore);
        sleep(1.0);
        gameTaskRepository.updateCreditGreaterThan(auditScore);
        entityManager.clear();
        List<GameTask> updatedGameTasks = gameTaskRepository.findGameTasksByScoreGreaterThan(auditScore);
        return updatedGameTasks;
    }

    private String getGameTaskString(List<GameTask> gameTasks) {
        return gameTasks.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    // [SQLITE_BUSY]  The database file is locked (database is locked). So SQLite need retried it.
    @Retryable(value = {TransactionException.class}, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @Transactional
    public void createGameTask(String name, Integer score) {
        sleep(0.5);
        gameTaskRepository.save(GameTask.create(name, score));
    }


}
