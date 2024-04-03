package com.jedi.isolationlevel.controller1;

import com.jedi.isolationlevel.isolation.IsolationOperate;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.service.SeedService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Isolation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = {"classpath:/database-setting.properties"})
public abstract class BaseIsolationControllerTest extends BaseControllerTest implements IsolationOperate {
    private final Map<Isolation, String> resultMap = new LinkedHashMap<>();

    @Autowired
    protected SeedService seedService;

    @AfterAll
    public void destroyAll() {
        System.out.println("===========================================");
        String title = String.format("|  Уровни изоляции   |  %-16s  |", getIsolationIssue());
        System.out.println(title);
        System.out.println("-------------------------------------------");
        resultMap.forEach((key, value) -> {
            String lineResult = String.format("|  %-16s  |  %-16s  |", key, value);
            System.out.println(lineResult);
        });
        System.out.println("===========================================");
    }

    protected <T> void executeTemplate(Isolation isolation, T resultData, Function<T, IsolationResult> occurSupplier) {
        printObject(isolation, resultData);
        IsolationResult result = occurSupplier.apply(resultData);
        printResult(isolation, result.getOccur());
        setResultMap(isolation, result.getOccur());
    }

    private void printObject(Isolation isolation, Object obj) {
        System.out.println("[" + isolation.name() + "] data: " + obj.toString());
    }

    private void printResult(Isolation isolation, boolean occur) {
        System.out.println("[" + isolation.name() + "] " + getIsolationIssue() + ": " + (occur ? "возникает !" : "не возникает!"));
    }

    private void setResultMap(Isolation isolation, boolean occur) {
        resultMap.put(isolation, (occur) ? "возникает" : "не возникает");
    }

    protected abstract String getIsolationIssue();
}
