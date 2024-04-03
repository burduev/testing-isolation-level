package com.jedi.isolationlevel.isolation.lostupdate;

import com.jedi.isolationlevel.isolation.IsolationIssueTemplate;
import com.jedi.isolationlevel.isolation.Ticket;
import com.jedi.isolationlevel.model.LostUpdateExpectOccur;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseLostUpdate extends IsolationIssueTemplate<Ticket, LostUpdateExpectOccur> {

    @Override
    protected final void assertOccur(Ticket actual, LostUpdateExpectOccur expectOccur) {
        assertThat(actual).isNotNull();
        assertThat(expectOccur).isNotNull();

        // actual: 9, expect Occur: 9
        assertThat(actual.getQuantity()).isEqualTo(expectOccur.getIncorrectQuantity());
    }

    @Override
    protected final void assertNotOccur(Ticket actual, LostUpdateExpectOccur expectOccur) {
        assertThat(actual).isNotNull();
        assertThat(expectOccur).isNotNull();

        // actual: 5, expect: 5
//        System.out.println("Actual: " +actual.getQuantity());
//        System.out.println("expect: " +expectOccur.getCorrectQuantity());
        assertThat(actual.getQuantity()).isEqualTo(expectOccur.getCorrectQuantity());
    }
}
