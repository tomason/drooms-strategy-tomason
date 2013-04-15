package org.drooms.strategy.tomason.rules;

import org.drooms.strategy.tomason.test.RuleSetTest;
import org.drooms.strategy.tomason.tools.RuleSet;

public class StopWatchRulesTest extends RuleSetTest {
    private static final RuleSet RULES = new StopWatchRules();

    @Override
    protected RuleSet getTestedRules() {
        return RULES;
    }
}
