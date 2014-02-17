package cz.schlosserovi.tomas.drooms.strategy.domain.status;

public class StopProcessing {
    private int turn;

    public StopProcessing() {
        this(-1);
    }

    public StopProcessing(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return String.format("StopProcessing[turn='%s']", turn);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + turn;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof StopProcessing)) {
            return false;
        }
        StopProcessing other = (StopProcessing) obj;
        if (turn != other.turn) {
            return false;
        }
        return true;
    }

}
