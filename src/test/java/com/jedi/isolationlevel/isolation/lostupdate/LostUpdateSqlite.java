package com.jedi.isolationlevel.isolation.lostupdate;

import com.jedi.isolationlevel.isolation.Ticket;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.model.LostUpdateExpectOccur;

public class LostUpdateSqlite extends BaseLostUpdate {
    @Override
    public IsolationResult assertDEFAULT(Ticket actual, LostUpdateExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public IsolationResult assertREAD_UNCOMMITTED(Ticket actual, LostUpdateExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public final IsolationResult assertREAD_COMMITTED(Ticket inventory, LostUpdateExpectOccur lostUpdateExpectOccur) {
        throw new UnsupportedOperationException("SQLite supports only TRANSACTION_SERIALIZABLE and TRANSACTION_READ_UNCOMMITTED.");
    }

    @Override
    public final IsolationResult assertREPEATABLE_READ(Ticket inventory, LostUpdateExpectOccur lostUpdateExpectOccur) {
        throw new UnsupportedOperationException("SQLite supports only TRANSACTION_SERIALIZABLE and TRANSACTION_READ_UNCOMMITTED.");
    }
}
