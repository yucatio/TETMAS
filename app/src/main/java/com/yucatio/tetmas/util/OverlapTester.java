package com.yucatio.tetmas.util;

import android.graphics.PointF;

import com.yucatio.tetmas.view.ScreenLayout;

public class OverlapTester {
    public static boolean overlapPointRectangle(PointF point, float[] vertexs) {
        return overlapPointRectangle(point,
                ScreenLayout.getTopLeft(vertexs),
                ScreenLayout.getWidth(vertexs),
                ScreenLayout.getHeight(vertexs)
        );
    }

    public static boolean overlapPointRectangle(float x, float y, float[] vertexs) {
        return overlapPointRectangle(
                x,
                y,
                ScreenLayout.getLeft(vertexs),
                ScreenLayout.getTop(vertexs),
                ScreenLayout.getWidth(vertexs),
                ScreenLayout.getHeight(vertexs));
    }

    public static boolean overlapPointRectangle(PointF point, PointF upperLeft, float width, float height) {
        return point.x >= upperLeft.x &&
                point.x <= upperLeft.x + width &&
                point.y >= upperLeft.y &&
                point.y <= upperLeft.y + height;
    }

    public static boolean overlapPointRectangle(float pointX, float pointY, float left, float top, float width, float height) {
        return pointX >= left &&
                pointX <= left + width &&
                pointY >= top &&
                pointY <= top + height;
    }

    public static boolean overlapPointCircle(PointF touchPoint, PointF center, float radius) {

        // touchPointとの距離
        double distance =Math.sqrt(Math.pow(touchPoint.x - center.x, 2) + Math.pow(touchPoint.y - center.y, 2));

        return distance < radius;
    }
}
