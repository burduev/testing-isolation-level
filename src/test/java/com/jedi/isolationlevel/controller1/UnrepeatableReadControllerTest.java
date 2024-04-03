package com.jedi.isolationlevel.controller1;

import com.jedi.isolationlevel.isolation.Inventory;
import com.jedi.isolationlevel.isolation.IsolationIssueDelegate;
import com.jedi.isolationlevel.isolation.unrepeatableread.BaseUnrepeatableRead;
import com.jedi.isolationlevel.model.UnrepeatableReadExpectOccur;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Isolation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UnrepeatableReadControllerTest extends BaseIsolationControllerTest {
    private static final String ACQUIRE_ENDPOINT = "/unrepeatable-read/acquire";
    private static final String PURCHASE_ENDPOINT = "/unrepeatable-read/purchase";

    private final UnrepeatableReadExpectOccur expectOccur
            = new UnrepeatableReadExpectOccur(0, 900, 0, 900);

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private BaseUnrepeatableRead unrepeatableRead;

    @BeforeAll
    @Override
    public void setupAll() {
        super.setupAll();
        System.out.println("[Reset Inventory]");
        seedService.resetInventory(expectOccur.getDefaultQuantity(), 5);

        System.out.println("======================================");
        System.out.println("|  Acquire(T1)  | Purchase(T2)       |");
        System.out.println("--------------------------------------");
        System.out.println("|  Read(0)      |                    |");
        System.out.println("|               |  Read(0)           |");
        System.out.println("|               |  Purchase(0 +900)  |");
        System.out.println("|               |  Commit            |");
        System.out.println("|  Read(900)    |                    |");
        System.out.println("--------------------------------------");
        System.out.println("[Не возникает Dirty Read]  Значение " + expectOccur.getCorrectQuantity());   // 0
        System.out.println("[Возникает Dirty Read]     Значение " + expectOccur.getIncorrectQuantity()); // 900
        System.out.println("======================================");
    }

    @Test
    @Order(1)
    @Override
    @Disabled
    public void test_DEFAULT() throws Exception {
        testUnrepeatableRead(1, Isolation.DEFAULT, unrepeatableRead::assertDEFAULT);
    }

    @Test
    @Order(2)
    @Override
    public void test_READ_UNCOMMITTED() throws Exception {
        testUnrepeatableRead(2, Isolation.READ_UNCOMMITTED, unrepeatableRead::assertREAD_UNCOMMITTED);
    }

    @Test
    @Order(3)
    @Override
    @DisabledIf(expression = "#{environment.acceptsProfiles('sqlite')}", loadContext = true, reason = "SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.")
    public void test_READ_COMMITTED() throws Exception {
        testUnrepeatableRead(3, Isolation.READ_COMMITTED, unrepeatableRead::assertREAD_COMMITTED);
    }

    @Test
    @Order(4)
    @Override
    @DisabledIf(expression = "#{environment.acceptsProfiles('sqlite')}", loadContext = true, reason = "SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.")
    public void test_REPEATABLE_READ() throws Exception {
        testUnrepeatableRead(4, Isolation.REPEATABLE_READ, unrepeatableRead::assertREPEATABLE_READ);
    }

    @Test
    @Order(5)
    @Override
    public void test_SERIALIZABLE() throws Exception {
        testUnrepeatableRead(5, Isolation.SERIALIZABLE, unrepeatableRead::assertSERIALIZABLE);
    }

    private void testUnrepeatableRead(long inventoryId, Isolation isolation, UnrepeatableReadDelegate delegate) throws Exception {
        Inventory inventory = unrepeatableRead(inventoryId, isolation);
        executeTemplate(isolation, inventory, (data) -> delegate.assertResult(data, expectOccur));
    }

    private Inventory unrepeatableRead(long inventoryId, Isolation isolation) throws Exception {
        // Создание нескольких потоков для симуляции параллелизма.
        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Acquire inventory
        Future<ResultActions> t1 = executorService.submit(() -> {
            String url = String.format("%s/%s", ACQUIRE_ENDPOINT, inventoryId);
            return performGet(url, (builder) -> builder.param("isolation", isolation.name()));
        });

        // Purchase inventory
        Future<ResultActions> t2 = executorService.submit(() -> {
            String url = String.format("%s/%s", PURCHASE_ENDPOINT, inventoryId);
            return performPost(url, (builder) -> builder.param("quantity", String.valueOf(expectOccur.getPurchaseQuantityT2())));
        });

        // Waiting Response
        t2.get().andExpect(STATUS_OK);
        ResultActions resultActions = t1.get().andExpect(STATUS_OK);

        // Get the Inventory
        return fromResult(resultActions, Inventory.class);
    }

    @Override
    protected String getIsolationIssue() {
        return "Unrepeatable Read";
    }

    private interface UnrepeatableReadDelegate extends IsolationIssueDelegate<Inventory, UnrepeatableReadExpectOccur> {
    }
}
