package com.jedi.isolationlevel.service;

import com.jedi.isolationlevel.isolation.Account2;
import com.jedi.isolationlevel.repository.AccountRepository2;
import lombok.RequiredArgsConstructor;
import org.hibernate.TransactionException;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TrueLostUpdateService extends BaseService {
    private final AccountRepository2 accountRepository2;
    private TrueLostUpdateService proxy;

    public void deposit(Long id, Integer amount) {
        Account2 account = getAccount(id);

        int beforeDepositAmount = account.getAmount();
        int afterDepositAmount = beforeDepositAmount + amount;
        sleep(1.0);
        account.setAmount(afterDepositAmount);
        accountRepository2.saveAndFlush(account);
        sleep(1.5);
    }

    @Transactional(isolation = Isolation.DEFAULT)
    public void withdrawDefault(Long id, Integer amount) {
        withdraw(id, amount);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void withdrawReadUncommitted(Long id, Integer amount) {
        withdraw(id, amount);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void withdrawReadCommitted(Long id, Integer amount) {
        withdraw(id, amount);
    }

    @Retryable(value = LockAcquisitionException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void withdrawRepeatableRead(Long id, Integer amount) {
        withdraw(id, amount);
    }

    @Retryable(value = {LockAcquisitionException.class, TransactionException.class}, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void withdrawSerializable(Long id, Integer amount) {
        withdraw(id, amount);
    }

    public void withdraw(Long id, Integer amount) {
        sleep(0.5);
        Account2 account = getAccount(id);

        sleep(1.5);
        int beforeWithdrawAmount = account.getAmount();
        int afterWithdrawAmount = account.getAmount() - amount;
        sleep(1.5);
        account.setAmount(afterWithdrawAmount);
        accountRepository2.saveAndFlush(account);
        sleep(1.0);
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    public Account2 getAccount(Long id) {
        return accountRepository2.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find the Account id " + id));
    }
}
