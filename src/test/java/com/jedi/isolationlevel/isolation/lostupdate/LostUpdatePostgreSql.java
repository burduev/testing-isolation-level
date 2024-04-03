package com.jedi.isolationlevel.isolation.lostupdate;

import com.jedi.isolationlevel.isolation.Ticket;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.model.LostUpdateExpectOccur;

public class LostUpdatePostgreSql extends BaseLostUpdate {
    @Override
    public IsolationResult assertREPEATABLE_READ(Ticket actual, LostUpdateExpectOccur expectOccur) {
        return notOccur(actual, expectOccur);
    }
}
