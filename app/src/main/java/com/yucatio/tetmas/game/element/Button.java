package com.yucatio.tetmas.game.element;

import java.io.Serializable;

public class Button implements Serializable {
    public static final float ENABLE = 0.0f;
    public static final float SELECTED = 1.1f;
    public static final float DISABLE = 2.1f;
    public static final float TEMPORARY_DISABLE = 3.0f;

    private float state;

    public Button() {
        this(ENABLE);
    }

    public Button(float state) {
        this.state = state;
    }

    public void setState(float state) {
        this.state = state;
    }

    public float getState() {
        return state;
    }
}
