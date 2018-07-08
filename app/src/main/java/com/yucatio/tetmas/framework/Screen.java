package com.yucatio.tetmas.framework;

import android.opengl.GLES20;

import com.yucatio.tetmas.game.view.GameWorldLayout;

public abstract class Screen {
    protected final Game game;

    /** レンダリング領域幅 (px) */
    private int renderWidth;
    /** レンダリング領域高さ (px) */
    private int renderHeight;

    public Screen(Game game) {
        this.game = game;
    }

    public void onSurfaceChanged(int width, int height) {
        float fieldRatio = GameWorldLayout.gameWorldHeight / GameWorldLayout.gameWorldWidth;
        if ((height * 1.0f)/(width * 1.0f) >= fieldRatio) {
            // 縦に長い
            renderWidth = width;
            renderHeight = (int)(renderWidth * fieldRatio);

        } else {
            // 横に長い
            renderHeight = height;
            renderWidth = (int)(renderHeight / fieldRatio);
        }

        GLES20.glViewport((width - renderWidth) / 2, (height - renderHeight) / 2, renderWidth, renderHeight);
    }

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public abstract Screen getPreviousScreen();

    public int getRenderWidth() {
        return renderWidth;
    }

    public int getRenderHeight() {
        return renderHeight;
    }

}
