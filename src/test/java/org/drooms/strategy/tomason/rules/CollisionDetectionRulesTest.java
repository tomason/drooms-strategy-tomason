package org.drooms.strategy.tomason.rules;

import org.drooms.strategy.tomason.test.RuleSetTest;
import org.drooms.strategy.tomason.tools.RuleSet;

public class CollisionDetectionRulesTest extends RuleSetTest {
    private static final RuleSet RULES = new CollisionDetectionRules();

    @Override
    protected RuleSet getTestedRules() {
        return RULES;
    }
}
