package com.yucatio.tetmas.game.element;

public enum Tetromino {
    I(new Point[][] {
        {new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3)},
        {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)}
    }, new Point[][]{
            {new Point(2, 0), new Point(2, 3)},
            {new Point(0, 1), new Point(3, 1)}
    }, new Point[][]{
            {new Point(1, 0), new Point(1,1), new Point(1, 2), new Point(1, 3),
                    new Point(2, 4), new Point(3, 3), new Point(3, 2), new Point(3, 1),
                    new Point(3, 0), new Point(2, -1)},
            {new Point(-1, 1), new Point(0, 2), new Point(1, 2), new Point(2, 2),
                    new Point(3, 2), new Point(4, 1), new Point(3, 0), new Point(2, 0),
                    new Point(1, 0), new Point(0, 0)}
    }, new Point[][]{
            {new Point(1, -1), new Point(1, 0), new Point(1,1), new Point(1, 2),
                    new Point(1, 3), new Point(1, 4), new Point(2, 4), new Point(3, 4),
                    new Point(3, 3), new Point(3, 2), new Point(3, 1), new Point(3, 0),
                    new Point(3, -1), new Point(2, -1)},
            {new Point(-1, 0), new Point(-1, 1), new Point(-1, 2), new Point(0, 2),
                    new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(4, 2),
                    new Point(4, 1), new Point(4, 0), new Point(3, 0), new Point(2, 0),
                    new Point(1, 0), new Point(0, 0)}
    }),
    O(new Point[][]{
        {new Point(1, 1), new Point(2, 1), new Point(1, 2), new Point(2, 2)}
    }, new Point[][]{
            {new Point(1, 1), new Point(2, 2)}
    }, new Point[][]{
            {new Point(0, 1), new Point(0, 2), new Point(1, 3), new Point(2, 3),
                    new Point(3, 2), new Point(3, 1), new Point(2, 0), new Point(1, 0)}
    }, new Point[][]{
            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3),
                    new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(3, 2),
                    new Point(3, 1), new Point(3, 0), new Point(2, 0), new Point(1, 0)}
    }),
    T(new Point[][] {
        {new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(1, 3)},
        {new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(1, 3)},
        {new Point(1, 1), new Point(0, 2), new Point(1, 2), new Point(1, 3)},
        {new Point(1, 1), new Point(0, 2), new Point(1, 2), new Point(2, 2)}
    }, new Point[][]{
            {new Point(1, 1), new Point(2, 3)},
            {new Point(0, 2), new Point(2, 3)},
            {new Point(0, 1), new Point(1, 3)},
            {new Point(0, 1), new Point(2, 2)}
    }, new Point[][]{
            {new Point(0, 1), new Point(0, 2), new Point(0, 3), new Point(1, 4),
                    new Point(2, 3), new Point(3, 2), new Point(2, 1), new Point(1, 0)},
            {new Point(-1, 2), new Point(0, 3), new Point(1, 4), new Point(2, 3),
                    new Point(3, 2), new Point(2, 1), new Point(1, 1), new Point(0, 1)},
            {new Point(0, 1), new Point(-1, 2), new Point(0, 3), new Point(1, 4),
                    new Point(2, 3), new Point(2, 2), new Point(2, 1), new Point(1, 0)},
            {new Point(0, 1), new Point(-1, 2), new Point(0, 3), new Point(1, 3),
                    new Point(2, 3), new Point(3, 2), new Point(2, 1), new Point(1, 0)}
    }, new Point[][] {
            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3),
                    new Point(0, 4), new Point(1, 4), new Point(2, 4), new Point(2, 3),
                    new Point(3, 3), new Point(3, 2), new Point(3, 1), new Point(2, 1),
                    new Point(2, 0), new Point(1, 0)},
            {new Point(-1, 1), new Point(-1, 2), new Point(-1, 3), new Point(0, 3),
                    new Point(0, 4), new Point(1, 4), new Point(2, 4), new Point(2, 3),
                    new Point(3, 3), new Point(3, 2), new Point(3, 1), new Point(2, 1),
                    new Point(1, 1), new Point(0, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(-1, 1), new Point(-1, 2),
                    new Point(-1, 3), new Point(0, 3), new Point(0, 4), new Point(1, 4),
                    new Point(2, 4), new Point(2, 3), new Point(2, 2), new Point(2, 1),
                    new Point(2, 0), new Point(1, 0)},
            {new Point(0, 0), new Point(0, 1), new Point(-1, 1), new Point(-1, 2),
                    new Point(-1, 3), new Point(0, 3), new Point(1, 3), new Point(2, 3),
                    new Point(3, 3), new Point(3, 2), new Point(3, 1), new Point(2, 1),
                    new Point(2, 0), new Point(1, 0)}
    }),
    J(new Point[][]{
        {new Point(2, 0), new Point(2, 1), new Point(1, 2), new Point(2, 2)},
        {new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2)},
        {new Point(1, 1), new Point(2, 1), new Point(1, 2), new Point(1, 3)},
        {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)}
    }, new Point[][]{
            {new Point(1, 0), new Point(2, 2)},
            {new Point(1, 1), new Point(3, 2)},
            {new Point(1, 1), new Point(2, 3)},
            {new Point(0, 1), new Point(2, 2)}
    }, new Point[][]{
            {new Point(0, 2), new Point(1, 3), new Point(2, 3), new Point(3, 2),
                    new Point(3, 1), new Point(3, 0), new Point(2, -1), new Point(1, 0),
                    new Point(1, 1)},
            {new Point(0, 1), new Point(0, 2), new Point(1, 3), new Point(2, 3),
                    new Point(3, 3), new Point(4, 2), new Point(3, 1), new Point(2, 1),
                    new Point(1, 0)},
            {new Point(0, 1), new Point(0, 2), new Point(0, 3), new Point(1, 4),
                    new Point(2, 3), new Point(2, 2), new Point(3, 1), new Point(2, 0),
                    new Point(1, 0)},
            {new Point(-1, 1), new Point(0, 2), new Point(1, 2), new Point(2, 3),
                    new Point(3, 2), new Point(3, 1), new Point(2, 0), new Point(1, 0),
                    new Point(0, 0)}
    }, new Point[][]{
            {new Point(0, 1), new Point(0, 2), new Point(0, 3), new Point(1, 3),
                    new Point(2, 3), new Point(3, 3), new Point(3, 2), new Point(3, 1),
                    new Point(3, 0), new Point(3, -1), new Point(2, -1), new Point(1, -1),
                    new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3),
                    new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(4, 3),
                    new Point(4, 2), new Point(4, 1), new Point(3, 1), new Point(2, 1),
                    new Point(2, 0), new Point(1, 0)},
            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3),
                    new Point(0, 4), new Point(1, 4), new Point(2, 4), new Point(2, 3),
                    new Point(2, 2), new Point(3, 2), new Point(3, 1), new Point(3, 0),
                    new Point(2, 0), new Point(1, 0)},
            {new Point(-1, 0), new Point(-1, 1), new Point(-1, 2), new Point(0, 2),
                    new Point(1, 2), new Point(1, 3), new Point(2, 3), new Point(3, 3),
                    new Point(3, 2), new Point(3, 1), new Point(3, 0), new Point(2, 0),
                    new Point(1, 0), new Point(0, 0)}
    }),
    L(new Point[][]{
        {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
        {new Point(1, 1), new Point(2, 1), new Point(3, 1), new Point(1, 2)},
        {new Point(1, 1), new Point(2, 1), new Point(2, 2), new Point(2, 3)},
        {new Point(2, 1), new Point(0, 2), new Point(1, 2), new Point(2, 2)}
    }, new Point[][]{
            {new Point(1, 0), new Point(2, 2)},
            {new Point(1, 1), new Point(3, 2)},
            {new Point(1, 1), new Point(2, 3)},
            {new Point(0, 1), new Point(2, 2)}
    }, new Point[][]{
            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 3),
                    new Point(2, 3), new Point(3, 2), new Point(2, 1), new Point(2, 0),
                    new Point(1, -1)},
            {new Point(0, 1), new Point(0, 2), new Point(1, 3), new Point(2, 2),
                    new Point(3, 2), new Point(4, 1), new Point(3, 0), new Point(2, 0),
                    new Point(1, 0)},
            {new Point(0, 1), new Point(1, 2), new Point(1, 3), new Point(2, 4),
                    new Point(3, 3), new Point(3, 2), new Point(3, 1), new Point(2, 0),
                    new Point(1, 0)},
            {new Point(-1, 2), new Point(0, 3), new Point(1, 3), new Point(2, 3),
                    new Point(3, 2), new Point(3, 1), new Point(2, 0), new Point(1, 1),
                    new Point(0, 1)}
    }, new Point[][]{
            {new Point(0, -1), new Point(0, 0), new Point(0, 1), new Point(0, 2),
                    new Point(0, 3), new Point(1, 3), new Point(2, 3), new Point(3, 3),
                    new Point(3, 2), new Point(3, 1), new Point(2, 1), new Point(2, 0),
                    new Point(2, -1), new Point(1, -1)},
            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3),
                    new Point(1, 3), new Point(2, 3), new Point(2, 2), new Point(3, 2),
                    new Point(4, 2), new Point(4, 1), new Point(4, 0), new Point(3, 0),
                    new Point(2, 0), new Point(1, 0)},
            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 2),
                    new Point(1, 3), new Point(1, 4), new Point(2, 4), new Point(3, 4),
                    new Point(3, 3), new Point(3, 2), new Point(3, 1), new Point(3, 0),
                    new Point(2, 0), new Point(1, 0)},
            {new Point(-1, 1), new Point(-1, 2), new Point(-1, 3), new Point(0, 3),
                    new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(3, 2),
                    new Point(3, 1), new Point(3, 0), new Point(2, 0), new Point(1, 0),
                    new Point(1, 1), new Point(0, 1)}
    }),
    S(new Point[][]{
        {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
        {new Point(1, 1), new Point(2, 1), new Point(0, 2), new Point(1, 2)}
    }, new Point[][]{
            {new Point(1, 0), new Point(2, 2)},
            {new Point(0, 1), new Point(2, 2)}
    }, new Point[][] {
            {new Point(0, 0), new Point(0, 1), new Point(1, 2), new Point(2, 3),
                    new Point(3, 2), new Point(3, 1), new Point(2, 0), new Point(1, -1)},
            {new Point(-1, 2), new Point(0, 3), new Point(1, 3), new Point(2, 2),
                    new Point(3, 1), new Point(2, 0), new Point(1, 0), new Point(0, 1)}
    }, new Point[][] {
            {new Point(0, -1), new Point(0, 0), new Point(0, 1), new Point(0, 2),
                    new Point(1, 2), new Point(1, 3), new Point(2, 3), new Point(3, 3),
                    new Point(3, 2), new Point(3, 1), new Point(3, 0), new Point(2, 0),
                    new Point(2, -1), new Point(1, -1)},
            {new Point(-1, 1), new Point(-1, 2), new Point(-1, 3), new Point(0, 3),
                    new Point(1, 3), new Point(2, 3), new Point(2, 2), new Point(3, 2),
                    new Point(3, 1), new Point(3, 0), new Point(2, 0), new Point(1, 0),
                    new Point(0, 0), new Point(0, 1)}
    }),
    Z(new Point[][]{
        {new Point(2, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
        {new Point(1, 1), new Point(2, 1), new Point(2, 2), new Point(3, 2)}
    }, new Point[][]{
            {new Point(1, 0), new Point(2, 2)},
            {new Point(1, 1), new Point(3, 2)}
    }, new Point[][]{
            {new Point(0, 1), new Point(0, 2), new Point(1, 3), new Point(2, 2),
                    new Point(3, 1), new Point(3, 0), new Point(2, -1), new Point(1, 0)},
            {new Point(0, 1), new Point(1, 2), new Point(2, 3), new Point(3, 3),
                    new Point(4, 2), new Point(3, 1), new Point(2, 0), new Point(1, 0)}
    }, new Point[][]{
            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3),
                    new Point(1, 3), new Point(2, 3), new Point(2, 2), new Point(3, 2),
                    new Point(3, 1), new Point(3, 0), new Point(3, -1), new Point(2, -1),
                    new Point(1, -1), new Point(1, 0)},
            {new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 2),
                    new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(4, 3),
                    new Point(4, 2), new Point(4, 1), new Point(3, 1), new Point(3, 0),
                    new Point(2, 0), new Point(1, 0)}
    });

    private Point[][] tetrominoArray;
    private Point[][] boundingBox;
    private Point[][] fourNeighbors;
    private Point[][] eightNeighbors;
    private Tetromino(Point[][] tetrominoArray, Point[][] boundingBox, Point[][] fourNeighbors, Point[][] eightNeighbors){
        this.tetrominoArray = tetrominoArray;
        this.boundingBox = boundingBox;
        this.fourNeighbors = fourNeighbors;
        this.eightNeighbors = eightNeighbors;
    }

    public int getSpinNum() {
        return tetrominoArray.length;
    }

    public Point[] getTetrominoPoints(int spin) {
        spin %= getSpinNum();
        return tetrominoArray[spin];
    }

    public Point[] getBoundingBox(int spin) {
        spin %= getSpinNum();
        return boundingBox[spin];
    }

    public Point[] getFourNeighbors(int spin) {
        spin %= getSpinNum();
        return fourNeighbors[spin];
    }

    public Point[] getEightNeighbors(int spin) {
        spin %= getSpinNum();
        return eightNeighbors[spin];
    }
}
