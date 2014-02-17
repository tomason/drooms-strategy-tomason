package cz.schlosserovi.tomas.drooms.strategy.domain;

import org.drooms.api.Edge;
import org.drooms.api.Move;
import org.drooms.api.Node;

public class Functions {

    public static Move getMove(Node start, Node target) {
        int deltaX = start.getX() - target.getX();
        int deltaY = start.getY() - target.getY();

        if (deltaX > 0)
            return Move.LEFT;
        if (deltaX < 0)
            return Move.RIGHT;

        if (deltaY > 0)
            return Move.DOWN;
        if (deltaY < 0)
            return Move.UP;

        return Move.STAY;
    }

    public static Move getMove(Node start, Edge move) {
        return getMove(start, getTargetNode(start, move));
    }

    public static Node getTargetNode(Node start, Edge move) {
        if (move.getNodes().left.equals(start)) {
            return move.getNodes().right;
        }

        if (move.getNodes().right.equals(start)) {
            return move.getNodes().left;
        }

        return Node.getNode(0, 0);
    }

    public static Node getNode(Node start, Move direction) {
        switch (direction) {
            case LEFT:
                return Node.getNode(start.getX() - 1, start.getY());
            case RIGHT:
                return Node.getNode(start.getX() + 1, start.getY());
            case UP:
                return Node.getNode(start.getX(), start.getY() + 1);
            case DOWN:
                return Node.getNode(start.getX(), start.getY() - 1);
            default:
                return null;
        }
    }

    public static int getPriority(int points, int distance) {
        return points * 10000 / distance;
        //return 10000 / distance;
    }
}
