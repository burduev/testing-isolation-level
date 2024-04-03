package com.jedi.isolationlevel.conroller2;

import com.jedi.isolationlevel.isolation.Account2;
import com.jedi.isolationlevel.isolation.IsolationIssueDelegate;
import com.jedi.isolationlevel.isolation.truelostupdate.BaseTrueLostUpdate;
import com.jedi.isolationlevel.controller1.BaseIsolationControllerTest;
import com.jedi.isolationlevel.model.TrueLostUpdateExpectOccur;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Isolation;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class TrueLostUpdateControllerTest extends BaseIsolationControllerTest {
    private static final String TRUE_LOST_UPDATE_DEPOSIT_ENDPOINT = "/lost-update-true/deposit";
    private static final String TRUE_LOST_UPDATE_WITHDRAW_ENDPOINT = "/lost-update-true/withdraw";
    private static final String TRUE_LOST_UPDATE_ACCOUNT_ENDPOINT = "/lost-update-true/account";

    private final TrueLostUpdateExpectOccur expectOccur
            = new TrueLostUpdateExpectOccur(0, 500, 300,
            500, 0);

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private BaseTrueLostUpdate baseTrueLostUpdate;

    @BeforeAll
    @Override
    public void setupAll() {
        super.setupAll();
        System.out.println("[Reset Account]");
        seedService.resetAccount2(expectOccur.getDefaultTotalAmount(), 5);

        System.out.println("=============================================");
        System.out.println("|  Deposit(T1)       | Withdraw(T2)         |");
        System.out.println("---------------------------------------------");
        System.out.println("|  Read(0)           |                      |");
        System.out.println("|                    |  Read(0)             |");
        System.out.println("|  deposit(0 + 500)  |                      |");
        System.out.println("|  save              |                      |");
        System.out.println("|                    |  Withdraw(500 - 300) |");
        System.out.println("|                    |  save                |");
        System.out.println("|  Commit            |                      |");
        System.out.println("|                    |  Rollback            |");
        System.out.println("---------------------------------------------");
        System.out.println("[Не возникает Lost Update]  Значение " + expectOccur.getCorrectTotalAmount());   // 500
        System.out.println("[Возникает Lost Update]     Значение " + expectOccur.getIncorrectTotalAmount()); // 0
        System.out.println("=============================================");
    }

    @Test
    @Order(1)
    @Override
    @Disabled
    @DisabledIf(expression = "#{environment.acceptsProfiles('sqlite')}", loadContext = true, reason = "SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.")
    public void test_DEFAULT() throws Exception {
        testDirtyRead(1, Isolation.DEFAULT, baseTrueLostUpdate::assertDEFAULT);
    }

    @Test
    @Order(2)
    @Override
    @DisabledIf(expression = "#{environment.acceptsProfiles('sqlite')}", loadContext = true, reason = "SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.")
    public void test_READ_UNCOMMITTED() throws Exception {
        testDirtyRead(2, Isolation.READ_UNCOMMITTED, baseTrueLostUpdate::assertREAD_UNCOMMITTED);
    }

    @Test
    @Order(3)
    @Override
    @DisabledIf(expression = "#{environment.acceptsProfiles('sqlite')}", loadContext = true, reason = "SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.")
    public void test_READ_COMMITTED() throws Exception {
        testDirtyRead(3, Isolation.READ_COMMITTED, baseTrueLostUpdate::assertREAD_COMMITTED);
    }

    @Test
    @Order(4)
    @Override
    @DisabledIf(expression = "#{environment.acceptsProfiles('sqlite')}", loadContext = true, reason = "SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.")
    public void test_REPEATABLE_READ() throws Exception {
        testDirtyRead(4, Isolation.REPEATABLE_READ, baseTrueLostUpdate::assertREPEATABLE_READ);
    }

    @Test
    @Order(5)
    @Override
    @DisabledIf(expression = "#{environment.acceptsProfiles('sqlite')}", loadContext = true, reason = "SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.")
    public void test_SERIALIZABLE() throws Exception {
        testDirtyRead(5, Isolation.SERIALIZABLE, baseTrueLostUpdate::assertSERIALIZABLE);
    }

    private void testDirtyRead(long accountId, Isolation isolation, TrueLostUpdateDelegate delegate) throws Exception {
        Account2 account = dirtyRead(accountId, expectOccur.getDepositAmount(), expectOccur.getWithdrawAmount(), isolation);
        executeTemplate(isolation, account, (data) -> delegate.assertResult(data, expectOccur));
    }

    public Account2 dirtyRead(long accountId, int depositAmount, int withdrawAmount, Isolation isolation) throws Exception {
        // Создание нескольких потоков для симуляции параллелизма.
        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Deposit Amount
        Future<ResultActions> t1 = executorService.submit(() -> {
            String url = String.format("%s/%s", TRUE_LOST_UPDATE_DEPOSIT_ENDPOINT, accountId);
            return performPost(url, (builder) -> builder.param("amount", String.valueOf(depositAmount)));
        });

        // Withdraw Amount
        Future<ResultActions> t2 = executorService.submit(() -> {
            String url = String.format("%s/%s", TRUE_LOST_UPDATE_WITHDRAW_ENDPOINT, accountId);
            return performPost(url, (builder) -> {
                builder.param("amount", String.valueOf(withdrawAmount));
                builder.param("isolation", isolation.name());
            });
        });

        // Waiting Response
        Collection<Future<ResultActions>> futures = new LinkedList<>(Arrays.asList(t1, t2));
        for (Future<ResultActions> future : futures) {
            ResultActions actions = future.get();
            actions.andExpect(STATUS_OK);
        }

        // Check Result
        ResultActions resultActions = performGet(String.format("%s/%s", TRUE_LOST_UPDATE_ACCOUNT_ENDPOINT, accountId))
                .andExpect(STATUS_OK);

        // Get the Account
        return fromResult(resultActions, Account2.class);
    }

    @Override
    protected String getIsolationIssue() {
        return "Dirty Read";
    }

    private interface TrueLostUpdateDelegate extends IsolationIssueDelegate<Account2, TrueLostUpdateExpectOccur> {
    }
}
