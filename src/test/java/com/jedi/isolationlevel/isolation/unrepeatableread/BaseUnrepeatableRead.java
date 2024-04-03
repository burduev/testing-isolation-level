package com.jedi.isolationlevel.isolation.unrepeatableread;

import com.jedi.isolationlevel.isolation.Inventory;
import com.jedi.isolationlevel.isolation.IsolationIssueTemplate;
import com.jedi.isolationlevel.model.UnrepeatableReadExpectOccur;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseUnrepeatableRead extends IsolationIssueTemplate<Inventory, UnrepeatableReadExpectOccur> {
    @Override
    protected final void assertOccur(Inventory actual, UnrepeatableReadExpectOccur expectOccur) {
        assertThat(actual).isNotNull();
        assertThat(expectOccur).isNotNull();

        assertThat(actual.getQuantity()).isEqualTo(expectOccur.getIncorrectQuantity());
    }

    @Override
    protected final void assertNotOccur(Inventory actual, UnrepeatableReadExpectOccur expectOccur) {
        assertThat(actual).isNotNull();
        assertThat(expectOccur).isNotNull();

        assertThat(actual.getQuantity()).isEqualTo(expectOccur.getCorrectQuantity());
    }
}
