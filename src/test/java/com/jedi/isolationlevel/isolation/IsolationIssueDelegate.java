package com.jedi.isolationlevel.isolation;

import com.jedi.isolationlevel.model.IsolationResult;

@FunctionalInterface
public interface IsolationIssueDelegate<Actual, ExpectOccur> {
    IsolationResult assertResult(Actual actual, ExpectOccur expectOccur);
}
