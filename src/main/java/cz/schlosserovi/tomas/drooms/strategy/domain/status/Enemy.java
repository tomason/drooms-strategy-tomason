package cz.schlosserovi.tomas.drooms.strategy.domain.status;

import java.util.Deque;
import java.util.LinkedList;

import org.drooms.api.Node;
import org.drooms.api.Player;

//@PropertyReactive
public class Enemy {
    private Player player;
    private Deque<Node> worm;
    private int score;

    public Enemy(Player player) {
        this.player = player;
        worm = new LinkedList<>();
        score = 0;
    }

    public Enemy(Player player, Deque<Node> worm) {
        this.player = player;
        this.worm = worm;
        score = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Deque<Node> getWorm() {
        return worm;
    }

    //@Modifies({"worm", "head", "x", "y"})
    public void setWorm(Deque<Node> worm) {
        this.worm = worm;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //@Modifies("score")
    public void addScore(int score) {
        this.score += score;
    }

    public Node getHead() {
        return worm.getFirst();
    }

    public int getX() {
        return getHead().getX();
    }

    public int getY() {
        return getHead().getY();
    }
}
