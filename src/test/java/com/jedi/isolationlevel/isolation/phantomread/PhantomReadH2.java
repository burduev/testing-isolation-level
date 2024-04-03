package com.jedi.isolationlevel.isolation.phantomread;

import com.jedi.isolationlevel.model.PhantomReadExpectOccur;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.isolation.GameTask;

import java.util.List;

public class PhantomReadH2 extends BasePhantomRead {
    @Override
    public IsolationResult assertREPEATABLE_READ(List<GameTask> actual, PhantomReadExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }
}
