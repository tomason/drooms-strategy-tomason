package cz.schlosserovi.tomas.drooms.strategy.domain.status;

public class TimeHolder {
    private long time;
    private int turn;

    public TimeHolder(long time, int turn) {
        this.time = time;
        this.turn = turn;
    }

    public long getTime() {
        return time;
    }

    public int getTurn() {
        return turn;
    }
    
    public long getDelta(long newTime) {
        return newTime - time;
    }
}
