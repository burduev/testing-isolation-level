package com.jedi.isolationlevel.isolation.lostupdate;

import com.jedi.isolationlevel.isolation.Ticket;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.model.LostUpdateExpectOccur;

public class LostUpdateH2 extends BaseLostUpdate {
    @Override
    public IsolationResult assertSERIALIZABLE(Ticket actual, LostUpdateExpectOccur expectOccur) {
        return occur(actual, expectOccur);
    }
}
