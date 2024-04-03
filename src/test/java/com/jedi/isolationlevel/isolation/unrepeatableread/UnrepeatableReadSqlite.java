package com.jedi.isolationlevel.isolation.unrepeatableread;

import com.jedi.isolationlevel.isolation.Inventory;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.model.UnrepeatableReadExpectOccur;

public class UnrepeatableReadSqlite extends BaseUnrepeatableRead {

    @Override
    public IsolationResult assertDEFAULT(Inventory inventory, UnrepeatableReadExpectOccur unrepeatableReadExpectOccur) {
        return notOccur(inventory, unrepeatableReadExpectOccur);
    }

    @Override
    public IsolationResult assertREAD_UNCOMMITTED(Inventory inventory, UnrepeatableReadExpectOccur unrepeatableReadExpectOccur) {
        return notOccur(inventory, unrepeatableReadExpectOccur);
    }

    @Override
    public final IsolationResult assertREAD_COMMITTED(Inventory inventory, UnrepeatableReadExpectOccur unrepeatableReadExpectOccur) {
        throw new UnsupportedOperationException("SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.");
    }

    @Override
    public final IsolationResult assertREPEATABLE_READ(Inventory inventory, UnrepeatableReadExpectOccur unrepeatableReadExpectOccur) {
        throw new UnsupportedOperationException("SQLite поддерживает только TRANSACTION_SERIALIZABLE и TRANSACTION_READ_UNCOMMITTED.");
    }
}
