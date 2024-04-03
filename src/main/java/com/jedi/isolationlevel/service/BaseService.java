package com.jedi.isolationlevel.service;

import java.util.concurrent.TimeUnit;

public class BaseService {
    protected void sleep(double timeoutSec) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (timeoutSec * 1000));
        } catch (InterruptedException ignored) {
        }
    }
}
