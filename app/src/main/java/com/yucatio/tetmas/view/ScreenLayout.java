package com.yucatio.tetmas.view;

import android.graphics.PointF;

public class ScreenLayout {
    public static PointF getTopLeft(float[] vertexes) {
        return new PointF(vertexes[0], vertexes[1]);
    }

    public static float getLeft(float[] vertexes) {
        return vertexes[0];
    }

    public static float getTop(float[] vertexes) {
        return vertexes[1];
    }

    public static float getWidth(float[] vertexes) {
        return vertexes[6] - vertexes[0];
    }

    public static float getHeight(float[] vertexes) {
        return vertexes[4] - vertexes[1];
    }

    protected static void setVertexRect(float[] vertexes, float offsetX, float offsetY, float width, float height) {
        vertexes[0] = offsetX;         vertexes[1] = offsetY;           vertexes[2] = 0.0f;
        vertexes[3] = offsetX;         vertexes[4] = offsetY + height;  vertexes[5] = 0.0f;
        vertexes[6] = offsetX + width; vertexes[7] = offsetY;           vertexes[8] = 0.0f;
        vertexes[9] = offsetX + width; vertexes[10] = offsetY + height; vertexes[11] = 0.0f;
    }

}
