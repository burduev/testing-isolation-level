package com.jedi.isolationlevel.isolation.unrepeatableread;

import com.jedi.isolationlevel.isolation.Inventory;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.model.UnrepeatableReadExpectOccur;

public class UnrepeatableReadMySql extends BaseUnrepeatableRead {
    @Override
    public IsolationResult assertDEFAULT(Inventory actual, UnrepeatableReadExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }

    @Override
    public IsolationResult assertREPEATABLE_READ(Inventory actual, UnrepeatableReadExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }
}
