package org.drooms.strategy.tomason.components.collision;

import org.drooms.api.Node;

public class MyWorm extends AbstractObstacle {
    public MyWorm(Node position) {
        super(position);
    }

    public MyWorm(Node position, int expires) {
        super(position, expires);
    }

}
