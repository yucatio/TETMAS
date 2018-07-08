package com.yucatio.tetmas.game.attribute;

public class WinLossRecord {
    private int winCount;
    private int evenCount;
    private int lossCount;
    private long timestamp;

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getEvenCount() {
        return evenCount;
    }

    public void setEvenCount(int evenCount) {
        this.evenCount = evenCount;
    }

    public int getLossCount() {
        return lossCount;
    }

    public void setLossCount(int lossCount) {
        this.lossCount = lossCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WinLossRecord{" +
                "winCount=" + winCount +
                ", evenCount=" + evenCount +
                ", lossCount=" + lossCount +
                ", timestamp=" + timestamp +
                '}';
    }
}
