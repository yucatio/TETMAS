package com.yucatio.tetmas.game.element;

import java.io.Serializable;

public class TetrominoPosition implements Serializable {
    private Tetromino tetromino;
    private int x;
    private int y;
    private int spin;

    public TetrominoPosition(Tetromino tetromino, int x, int y, int spin) {
        this.tetromino = tetromino;
        this.x = x;
        this.y = y;
        this.spin = spin;
    }

    public void setTetromino(Tetromino tetromino) {
        this.tetromino = tetromino;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void translateX(int diffX) {
        this.x += diffX;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void translateY(int diffY) {
        this.y += diffY;
    }

    public void spin() {
        spin = (spin + 1) % tetromino.getSpinNum();
    }

    public void setSpin(int spin) {
        this.spin = spin;
    }

    public Tetromino getTetromino() {
        return tetromino;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpin() {
        return spin;
    }

    @Override
    public String toString() {
        return "TetrominoPosition{" +
                "tetromino=" + tetromino +
                ", x=" + x +
                ", y=" + y +
                ", spin=" + spin +
                '}';
    }
}
