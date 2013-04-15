package org.drooms.strategy.tomason.components;

import org.drools.definition.type.Modifies;
import org.drooms.api.Node;

public abstract class WithPosition {
    private Node position;

    public WithPosition(Node position) {
        this.position = position;
    }

    public Node getPosition() {
        return position;
    }

    @Modifies({"position", "x", "y"})
    public void setPosition(Node position) {
        this.position = position;
    }

    public int getX() {
        return position.getX();
    }

    public void setX(int x) {
    }

    public int getY() {
        return position.getY();
    }

    public void setY(int y) {
    }
}
