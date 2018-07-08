package com.yucatio.tetmas.io;

import android.view.View;

import com.yucatio.tetmas.framework.Input;

import java.util.List;

public class AndroidInput implements Input {
    private SingleTouchHandler touchHandler;

    public AndroidInput(View view) {
        touchHandler = new SingleTouchHandler(view);
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }

    @Override
    public void onSurfaceChanged(int width, int height, int renderWidth,
                                 int renderHeight) {
        touchHandler.onSurfaceChanged(width, height, renderWidth, renderHeight);
    }
}