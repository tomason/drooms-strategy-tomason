package org.drooms.strategy.tomason.rules;

import org.drooms.strategy.tomason.test.RuleSetTest;
import org.drooms.strategy.tomason.tools.RuleSet;

public class DangerAvoidanceRulesTest extends RuleSetTest {
    private static final RuleSet RULES = new DangerAvoidanceRules();

    @Override
    protected RuleSet getTestedRules() {
        return RULES;
    }
}
