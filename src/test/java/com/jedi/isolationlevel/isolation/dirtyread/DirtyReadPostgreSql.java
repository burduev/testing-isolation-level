package com.jedi.isolationlevel.isolation.dirtyread;

import com.jedi.isolationlevel.isolation.Account;
import com.jedi.isolationlevel.model.DirtyReadExpectOccur;
import com.jedi.isolationlevel.model.IsolationResult;

public class DirtyReadPostgreSql extends BaseDirtyRead {
    @Override
    public IsolationResult assertDEFAULT(Account actual, DirtyReadExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public IsolationResult assertREAD_UNCOMMITTED(Account actual, DirtyReadExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public IsolationResult assertREAD_COMMITTED(Account actual, DirtyReadExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public IsolationResult assertREPEATABLE_READ(Account actual, DirtyReadExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }
}
