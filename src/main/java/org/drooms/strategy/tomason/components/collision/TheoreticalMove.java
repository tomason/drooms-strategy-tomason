package org.drooms.strategy.tomason.components.collision;

import java.util.LinkedList;
import java.util.List;

import org.drooms.api.Move;
import org.drooms.api.Node;

public class TheoreticalMove {
    // treshold to not take longer than 1s computing time
    private static final int LIMIT_TRESHOLD = 25;
    // list of moves leading to this move
    private List<Node> moves;
    // length limit
    private int limit;
    // length remaining
    private int length;
    // moves limits (for my worm direction)
    private int turns;
    // count of consecutive STAY moves
    private int stayCount;
    // first move (from the head) that led to this node
    private Move move;
    // target of this theoretical move
    private Node node;

    // constructs first theoretical move of type Move.STAY
    public TheoreticalMove(Node node, int stayCount, int limit) {
        this.moves = new LinkedList<>();
        this.move = Move.STAY;
        this.limit = Math.min(limit, LIMIT_TRESHOLD);
        this.turns = limit + 1;
        this.length = limit;
        this.node = node;
        this.stayCount = stayCount + 1;
    }

    // constructs first theoretical move
    public TheoreticalMove(Node node, Move move, int limit) {
        this.moves = new LinkedList<>();
        this.move = move;
        this.limit = Math.min(limit, LIMIT_TRESHOLD);
        this.turns = limit;
        this.length = limit;
        this.node = node;
        stayCount = 0;
    }

    // constructs STAY consecutive move
    public TheoreticalMove(TheoreticalMove previous) {
        this.moves = new LinkedList<>(previous.getMoves());
        moves.add(previous.getNode());
        this.move = previous.getMove();
        this.limit = previous.getLimit() - 1;
        this.turns = previous.getTurns();
        this.length = previous.getLength() - 1;
        this.node = previous.getNode();
        stayCount = previous.getStayCount() + 1;
    }

    // constructs moving consecutive move
    public TheoreticalMove(Node node, TheoreticalMove previous) {
        this.moves = new LinkedList<>(previous.getMoves());
        moves.add(previous.getNode());
        this.move = previous.getMove();
        this.limit = previous.getLimit() - 1;
        this.turns = previous.getTurns() - 1;
        this.length = previous.getLength() - 1;
        this.node = node;
        stayCount = 0;
    }

    public List<Node> getMoves() {
        return moves;
    }

    public void setMoves(List<Node> moves) {
        this.moves = moves;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getX() {
        return node.getX();
    }

    public int getY() {
        return node.getY();
    }

    public int getStayCount() {
        return stayCount;
    }

    public void setStayCount(int stayCount) {
        this.stayCount = stayCount;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
