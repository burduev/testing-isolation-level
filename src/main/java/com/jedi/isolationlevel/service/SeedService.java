package com.jedi.isolationlevel.service;

import com.jedi.isolationlevel.isolation.Account2;
import com.jedi.isolationlevel.isolation.Account;
import com.jedi.isolationlevel.isolation.GameTask;
import com.jedi.isolationlevel.isolation.Inventory;
import com.jedi.isolationlevel.isolation.Ticket;
import com.jedi.isolationlevel.repository.AccountRepository;
import com.jedi.isolationlevel.repository.AccountRepository2;
import com.jedi.isolationlevel.repository.GameTaskRepository;
import com.jedi.isolationlevel.repository.InventoryRepository;
import com.jedi.isolationlevel.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class SeedService {
    private final AccountRepository accountRepository;
    private final AccountRepository2 accountRepository2;
    private final InventoryRepository inventoryRepository;
    private final GameTaskRepository gameTaskRepository;
    private final TicketRepository ticketRepository;

    public void resetGameTasks() {
        initGameTasks(true);
    }

    public void initGameTasks(boolean clear) {
        if (clear && gameTaskRepository.count() > 0) {
            gameTaskRepository.deleteAll();
        }

        gameTaskRepository.save(GameTask.create("Darren", 900));
        gameTaskRepository.save(GameTask.create("Frank", 700));
        gameTaskRepository.save(GameTask.create("Jimpo", 600));
        gameTaskRepository.save(GameTask.create("Mario", 300));
        gameTaskRepository.save(GameTask.create("Hank", 250));
    }

    public void resetAccount(int amount, int count) {
        initAccount(amount, count, true);
    }
    public void initAccount(int amount, int count, boolean clear) {
        if (clear && accountRepository.count() > 0) {
            accountRepository.deleteAll();
        }

        createSeedCount(count, () -> accountRepository.save(createAccount()));
    }

    public void resetAccount2(int amount, int count) {
        initAccount2(amount, count, true);
    }

    public void initAccount2(int amount, int count, boolean clear) {
        if (clear && accountRepository2.count() > 0) {
            accountRepository2.deleteAll();
        }

        createSeedCount(count, () -> accountRepository2.save(createAccount2()));
    }

    public void resetInventory(int quantity, int count) {
        initInventory(quantity, count, true);
    }

    public void initInventory(int quantity, int count, boolean clear) {
        if (clear && inventoryRepository.count() > 0) {
            inventoryRepository.deleteAll();
        }

        createSeedCount(count, () -> inventoryRepository.save(createInventory(quantity)));
    }

    public void resetTicket(int quantity, int count) {
        initTicket(quantity, count, true);
    }

    public void initTicket(int quantity, int count, boolean clear) {
        if (clear && ticketRepository.count() > 0) {
            ticketRepository.deleteAll();
        }

        createSeedCount(count, () -> ticketRepository.save(createTicket(quantity)));
    }

    private void createSeedCount(int count, Runnable runnable) {
        Stream.iterate(1, n -> n + 1)
                .limit(count)
                .forEach((i) -> runnable.run());
    }

    private Account createAccount() {
        Account account = new Account();
        account.setAmount(0);
        return account;
    }

    private Account2 createAccount2() {
        Account2 account = new Account2();
        account.setAmount(0);
        return account;
    }

    private Inventory createInventory(int quantity) {
        Inventory inventory = new Inventory();
        inventory.setQuantity(quantity);
        return inventory;
    }

    private Ticket createTicket(int quantity) {
        Ticket inventory = new Ticket();
        inventory.setQuantity(quantity);
        return inventory;
    }
}
