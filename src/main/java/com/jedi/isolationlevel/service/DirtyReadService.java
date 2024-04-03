package com.jedi.isolationlevel.service;

import com.jedi.isolationlevel.repository.AccountRepository;
import com.jedi.isolationlevel.isolation.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class DirtyReadService extends BaseService {
    private final AccountRepository accountRepository;

    @Transactional
    public void deposit(Long id, Integer amount) {
        Account account = getAccount(id);

        int beforeDepositAmount = account.getAmount();
        int afterDepositAmount = beforeDepositAmount + amount;
        account.setAmount(afterDepositAmount);
        accountRepository.saveAndFlush(account);
        sleep(1.0);

        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void withdrawRepeatableRead(Long id, Integer amount) {
        withdraw(id, amount);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void withdrawSerializable(Long id, Integer amount) {
        withdraw(id, amount);
    }

    public void withdraw(Long id, Integer amount) {
        sleep(0.5);
        Account account = getAccount(id);

        sleep(1.0);
        int beforeWithdrawAmount = account.getAmount();
        int afterWithdrawAmount = account.getAmount() - amount;
        account.setAmount(afterWithdrawAmount);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find the Account id " + id));
    }
}
