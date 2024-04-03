package com.jedi.isolationlevel.isolation.phantomread;

import com.jedi.isolationlevel.isolation.GameTask;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.model.PhantomReadExpectOccur;

import java.util.List;

public class PhantomReadSqlite extends BasePhantomRead {

    @Override
    public IsolationResult assertDEFAULT(List<GameTask> gameTasks, PhantomReadExpectOccur phantomReadExpectOccur) {
        return notOccur(gameTasks, phantomReadExpectOccur);
    }

    @Override
    public IsolationResult assertREAD_UNCOMMITTED(List<GameTask> gameTasks, PhantomReadExpectOccur phantomReadExpectOccur) {
        return notOccur(gameTasks, phantomReadExpectOccur);
    }

    @Override
    public IsolationResult assertREAD_COMMITTED(List<GameTask> gameTasks, PhantomReadExpectOccur phantomReadExpectOccur) {
        throw new UnsupportedOperationException("SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.");
    }

    @Override
    public IsolationResult assertREPEATABLE_READ(List<GameTask> gameTasks, PhantomReadExpectOccur phantomReadExpectOccur) {
        throw new UnsupportedOperationException("SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.");
    }
}
