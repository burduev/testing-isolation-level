package com.jedi.isolationlevel.isolation.truelostupdate;

import com.jedi.isolationlevel.isolation.Account2;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.model.TrueLostUpdateExpectOccur;

public class TrueLostUpdateSqlite extends BaseTrueLostUpdate {

    @Override
    public IsolationResult assertDEFAULT(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public final IsolationResult assertREAD_UNCOMMITTED(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public final IsolationResult assertREAD_COMMITTED(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        throw new UnsupportedOperationException("SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.");
    }

    @Override
    public IsolationResult assertREPEATABLE_READ(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        throw new UnsupportedOperationException("SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.");
    }
}
