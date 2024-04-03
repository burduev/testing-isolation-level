package com.jedi.isolationlevel.isolation.phantomread;

import com.jedi.isolationlevel.isolation.GameTask;
import com.jedi.isolationlevel.isolation.IsolationIssueTemplate;
import com.jedi.isolationlevel.model.PhantomReadExpectOccur;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BasePhantomRead extends IsolationIssueTemplate<List<GameTask>, PhantomReadExpectOccur> {
    protected final void assertOccur(List<GameTask> actual, PhantomReadExpectOccur expectOccur) {
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(expectOccur).isNotNull();

        assertThat(actual.size()).isEqualTo(expectOccur.getIncorrectCount());
    }

    protected final void assertNotOccur(List<GameTask> actual, PhantomReadExpectOccur expectOccur) {
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(expectOccur).isNotNull();

        assertThat(actual.size()).isEqualTo(expectOccur.getCorrectCount());
    }
}
