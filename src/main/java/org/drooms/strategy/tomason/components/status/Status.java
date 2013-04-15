package org.drooms.strategy.tomason.components.status;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import org.drooms.api.Move;
import org.drooms.api.Node;

public class Status {
    private Deque<Node> snake;
    private Move direction;
    private int score;
    private int stayCount;

    public Status() {
        snake = new LinkedList<Node>(Arrays.asList(Node.getNode(0, 0)));
        direction = Move.STAY;
        score = 0;
        stayCount = 0;
    }

    public void setSnake(Deque<Node> snake) {
        this.snake = snake;
    }

    public Deque<Node> getSnake() {
        return snake;
    }

    public void setDirection(Move direction) {
        this.direction = direction;
        if (direction == Move.STAY) {
            stayCount++;
        } else {
            stayCount = 0;
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Node getPosition() {
        return snake.getFirst();
    }
    
    public int getX() {
        return getPosition().getX();
    }
    
    public int getY() {
        return getPosition().getY();
    }

    public int getLength() {
        return snake.size();
    }

    public int getScore() {
        return score;
    }

    public Move getDirection() {
        return direction;
    }

    public Node getTail() {
        return snake.getLast();
    }

    public int getStayCount() {
        return stayCount;
    }

}
