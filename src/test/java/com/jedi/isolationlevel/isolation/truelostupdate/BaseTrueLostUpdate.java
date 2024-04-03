package com.jedi.isolationlevel.isolation.truelostupdate;

import com.jedi.isolationlevel.isolation.Account2;
import com.jedi.isolationlevel.isolation.IsolationIssueTemplate;
import com.jedi.isolationlevel.model.TrueLostUpdateExpectOccur;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseTrueLostUpdate extends IsolationIssueTemplate<Account2, TrueLostUpdateExpectOccur> {

    @Override
    protected void assertOccur(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        assertThat(actual).isNotNull();

        // actual: 500, expect: 500
        assertThat(actual.getAmount()).isEqualTo(expectOccur.getIncorrectTotalAmount());
    }

    @Override
    protected void assertNotOccur(Account2 actual, TrueLostUpdateExpectOccur expectOccur) {
        assertThat(actual).isNotNull();

        // actual: 0, expect: 0
        assertThat(actual.getAmount()).isEqualTo(expectOccur.getCorrectTotalAmount());
    }
}
