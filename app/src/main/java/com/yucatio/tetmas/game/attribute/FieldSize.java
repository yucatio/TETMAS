package com.yucatio.tetmas.game.attribute;

public enum FieldSize {
    TWENTY_SQUARE(20, 20, "twenty_sq"), FIFTEEN_SQUARE(15, 15, "fifteen_sq");

    private int width;
    private int height;
    private String id;

    private FieldSize(int width, int height, String id) {
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getId() {
        return id;
    }
}
