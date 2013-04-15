package org.drooms.strategy.tomason;

import org.drooms.api.Strategy;
import org.drooms.impl.util.DroomsTestHelper;

public class TomasonStrategyTest extends DroomsTestHelper {

    @Override
    public Strategy getStrategy() {
        return new TomasonStrategy();
    }

}
