package com.yucatio.tetmas.selectfieldsize.view;

import com.yucatio.tetmas.view.ScreenLayout;

public class SelectFieldSizeLayout extends ScreenLayout {
    public static float[] background = new float[12];
    public static float[] backScreen = new float[12];
    public static float[] stageDisplay = new float[12];
    public static float[] message = new float[12];
    public static float[] field15Button = new float[12];
    public static float[] field20Button = new float[12];
    public static float[] backButton = new float[12];

    static {
        setVertexRect(background, 0.0f, 0.0f, 34.0f, 40.0f);

        setVertexRect(backScreen, 7.0f, 7.0f, 20.0f, 12.0f);

        setVertexRect(stageDisplay, 7.0f, 2.0f, 20.0f, 4.17f);
        setVertexRect(message, 7.0f, 9.0f, 20.0f, 8.0f);

        float x = 7.0f; float y = 20.0f;
        float width = 20.0f; float height = 4.17f;
        float yInterval = 4.2f; float yGroupInterval = 0.4f;
        setVertexRect(field15Button, x, y, width, height);
        y += yInterval;
        setVertexRect(field20Button, x, y, width, height);
        y += yInterval;
        y += yGroupInterval;
        setVertexRect(backButton, x, y, width, height);
    }

}
