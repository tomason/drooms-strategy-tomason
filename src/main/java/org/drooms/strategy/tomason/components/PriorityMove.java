package org.drooms.strategy.tomason.components;

import org.drools.definition.type.Modifies;
import org.drools.definition.type.PropertyReactive;
import org.drooms.api.Edge;
import org.drooms.api.Move;
import org.drooms.api.Node;

@PropertyReactive
public class PriorityMove {
    private Move move;
    private Node target;
    private int priority;

    public PriorityMove(Node target, Move move, int priority) {
        this.target = target;
        this.move = move;
        this.priority = priority;
    }

    public PriorityMove(Node start, Edge move, int priority) {
        this.target = Functions.getTargetNode(start, move);
        this.move = Functions.getMove(start, target);
        this.priority = priority;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Modifies("priority")
    public void addPriority(int priority) {
        this.priority += priority;
    }

    @Override
    public String toString() {
        return String.format("PriorityMove[move='%s', priority='%s']", move, priority);
    }
}
