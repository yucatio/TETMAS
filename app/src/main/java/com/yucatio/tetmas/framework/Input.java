package com.yucatio.tetmas.framework;

import java.util.List;

public interface Input {
    public static class TouchEvent {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;

        public int type;
        public float x, y;
    }

    public List<TouchEvent> getTouchEvents();

    public void onSurfaceChanged(int width, int height, int renderWidth,
                                 int renderHeight);

}
