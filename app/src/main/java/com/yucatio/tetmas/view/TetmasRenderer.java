package com.yucatio.tetmas.view;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yucatio.tetmas.game.view.GameWorldLayout;
import com.yucatio.tetmas.util.GLUtil;

public abstract class TetmasRenderer {
    public static final int FLOAT_SIZE_BYTES = 4;

    // game座標 to GL座標
    private float[] gameMVPMatrix = new float[16];

    public TetmasRenderer() {
        // gane座標 to GL座標  Matrix setting
        Matrix.setIdentityM(gameMVPMatrix, 0);
        Matrix.translateM(gameMVPMatrix, 0, -1.0f, 1.0f, 0.0f);
        Matrix.scaleM(gameMVPMatrix, 0, 2.0f / GameWorldLayout.gameWorldWidth, -1.0f * 2.0f / GameWorldLayout.gameWorldHeight, 1.0f);
        Matrix.translateM(gameMVPMatrix, 0, GameWorldLayout.gameWorldOffsetX, GameWorldLayout.gameWorldOffsetY, 0);

    }

    public void render() {
        renderBackground();
        renderObjects();
    }

    public abstract void renderBackground();
    public abstract void renderObjects();

    public float[] getGameMVPMatrix() {
        return gameMVPMatrix;
    }

    protected void drawRect(int matrixHandle) {
        drawRect(matrixHandle, gameMVPMatrix);
    }

    protected void drawRect(int matrixHandle, float[] projectMatrix) {
        // 4角形の描画
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, projectMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLUtil.checkGlError("glDrawArrays");
    }

}
