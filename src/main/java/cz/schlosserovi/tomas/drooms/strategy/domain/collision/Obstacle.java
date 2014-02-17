package cz.schlosserovi.tomas.drooms.strategy.domain.collision;

import org.drooms.api.Node;

public class Obstacle extends AbstractObstacle {
    public Obstacle(Node position) {
        super(position);
    }

    public Obstacle(Node position, int expires) {
        super(position, expires);
    }

}
