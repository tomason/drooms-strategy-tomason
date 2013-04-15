package org.drooms.strategy.tomason.rules;

import org.drooms.strategy.tomason.test.RuleSetTest;
import org.drooms.strategy.tomason.tools.RuleSet;

public class StatusRulesTest extends RuleSetTest {
    private static final RuleSet RULES = new StatusRules();

    @Override
    protected RuleSet getTestedRules() {
        return RULES;
    }
}
