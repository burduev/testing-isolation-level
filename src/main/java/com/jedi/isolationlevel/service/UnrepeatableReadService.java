package com.jedi.isolationlevel.service;

import com.jedi.isolationlevel.isolation.Inventory;
import com.jedi.isolationlevel.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.TransactionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UnrepeatableReadService extends BaseService {
    private final InventoryRepository inventoryRepository;
    private final EntityManager entityManager;

    @Transactional(isolation = Isolation.DEFAULT)
    public Inventory acquireDEFAULT(Long id) {
        return acquire(id);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Inventory acquireREAD_UNCOMMITTED(Long id) {
        return acquire(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Inventory acquireREAD_COMMITTED(Long id) {
        return acquire(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Inventory acquireREPEATABLE_READ(Long id) {
        return acquire(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Inventory acquireSERIALIZABLE(Long id) {
        return acquire(id);
    }

    public Inventory acquire(Long id) {
        Inventory inventory = getInventory(id);

        sleep(1.0);

        entityManager.clear();
        inventory = getInventory(id);

        return inventory;
    }

    // (SQLite) [SQLITE_BUSY]  The database file is locked (database is locked). So SQLite need retried it.
    @Retryable(value = {TransactionException.class}, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @Transactional
    public void purchase(Long id, Integer quantity) {
        sleep(0.5);

        Inventory inventory = getInventory(id);

        Integer nowQuantity = inventory.getQuantity();
        inventory.setQuantity(nowQuantity + quantity);

        inventoryRepository.saveAndFlush(inventory);
    }

    public Inventory getInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return inventory;
    }
}

