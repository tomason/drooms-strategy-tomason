package org.drooms.strategy.tomason.components.collision;

import org.drooms.api.Node;

class AbstractObstacle {
    private Node position;
    private int expires;

    public AbstractObstacle(Node position) {
        this(position, -1);
    }

    public AbstractObstacle(Node position, int expires) {
        this.position = position;
        this.expires = expires;
    }

    public Node getPosition() {
        return position;
    }

    public void setPosition(Node position) {
        this.position = position;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public int getX() {
        return getPosition().getX();
    }

    public int getY() {
        return getPosition().getY();
    }
}
