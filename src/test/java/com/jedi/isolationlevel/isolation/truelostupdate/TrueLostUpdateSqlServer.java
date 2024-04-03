package com.jedi.isolationlevel.isolation.truelostupdate;

import com.jedi.isolationlevel.isolation.Account2;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.model.TrueLostUpdateExpectOccur;

public class TrueLostUpdateSqlServer extends BaseTrueLostUpdate {
    @Override
    public IsolationResult assertDEFAULT(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public IsolationResult assertREAD_UNCOMMITTED(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public IsolationResult assertREPEATABLE_READ(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public IsolationResult assertREAD_COMMITTED(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }
}
