package com.jedi.isolationlevel.isolation.dirtyread;

import com.jedi.isolationlevel.isolation.Account;
import com.jedi.isolationlevel.model.DirtyReadExpectOccur;
import com.jedi.isolationlevel.model.IsolationResult;

public class DirtyReadSqlite extends BaseDirtyRead {

    @Override
    public IsolationResult assertDEFAULT(Account actual, DirtyReadExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public final IsolationResult assertREAD_UNCOMMITTED(Account actual, DirtyReadExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public final IsolationResult assertREAD_COMMITTED(Account actual, DirtyReadExpectOccur expectOccur) {
        throw new UnsupportedOperationException("SQLite supports only TRANSACTION_SERIALIZABLE and TRANSACTION_READ_UNCOMMITTED.");
    }

    @Override
    public IsolationResult assertREPEATABLE_READ(Account actual, DirtyReadExpectOccur expectOccur) {
        throw new UnsupportedOperationException("SQLite supports only TRANSACTION_SERIALIZABLE and TRANSACTION_READ_UNCOMMITTED.");
    }
}
