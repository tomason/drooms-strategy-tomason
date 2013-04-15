package org.drooms.strategy.tomason.components.history;

import org.drooms.api.Move;

public class PastMove {
    private final int turn;
    private final Move move;

    public PastMove(int turn, Move move) {
        this.turn = turn;
        this.move = move;
    }

    public int getTurn() {
        return turn;
    }

    public Move getMove() {
        return move;
    }
}
