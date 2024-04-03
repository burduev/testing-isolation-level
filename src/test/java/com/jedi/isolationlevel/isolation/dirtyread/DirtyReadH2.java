package com.jedi.isolationlevel.isolation.dirtyread;

import com.jedi.isolationlevel.model.DirtyReadExpectOccur;
import com.jedi.isolationlevel.model.IsolationResult;
import com.jedi.isolationlevel.isolation.Account;

public class DirtyReadH2 extends BaseDirtyRead {

    @Override
    public IsolationResult assertDEFAULT(Account account, DirtyReadExpectOccur dirtyReadExpectOccur) {
        return notOccur(account, dirtyReadExpectOccur);
    }

    @Override
    public IsolationResult assertREAD_COMMITTED(Account account, DirtyReadExpectOccur dirtyReadExpectOccur) {
        return notOccur(account, dirtyReadExpectOccur);
    }

    @Override
    public IsolationResult assertREPEATABLE_READ(Account account, DirtyReadExpectOccur dirtyReadExpectOccur) {
        return notOccur(account, dirtyReadExpectOccur);
    }
}
