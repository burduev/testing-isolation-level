package com.jedi.isolationlevel.isolation.dirtyread;

import com.jedi.isolationlevel.isolation.Account;
import com.jedi.isolationlevel.isolation.IsolationIssueTemplate;
import com.jedi.isolationlevel.model.DirtyReadExpectOccur;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseDirtyRead extends IsolationIssueTemplate<Account, DirtyReadExpectOccur> {
    @Override
    protected final void assertOccur(Account actual, DirtyReadExpectOccur expectOccur) {
        assertThat(actual).isNotNull();

        // actual: -300, expect: -300
        assertThat(actual.getAmount()).isEqualTo(expectOccur.getIncorrectTotalAmount());
    }

    @Override
    protected final void assertNotOccur(Account actual, DirtyReadExpectOccur expectOccur) {
        assertThat(actual).isNotNull();

        // actual: 200, expect: 200
        assertThat(actual.getAmount()).isEqualTo(expectOccur.getCorrectTotalAmount());
    }
}
