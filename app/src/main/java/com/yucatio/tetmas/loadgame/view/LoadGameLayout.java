package com.yucatio.tetmas.loadgame.view;

import com.yucatio.tetmas.view.ScreenLayout;

public class LoadGameLayout extends ScreenLayout {
    public static float[] background = new float[12];
    public static float[] backScreen = new float[12];
    public static float[] stageDisplay = new float[12];
    public static float[] fieldSizeDisplay = new float[12];
    public static float[] message = new float[12];
    public static float[] resumeGameButton = new float[12];
    public static float[] newGameButton = new float[12];
    public static float[] backButton = new float[12];

    static {
        setVertexRect(background, 0.0f, 0.0f, 34.0f, 40.0f);
        setVertexRect(backScreen, 7.0f, 11.2f, 20.0f, 12.0f);

        setVertexRect(stageDisplay, 7.0f, 2.0f, 20.0f, 4.17f);
        setVertexRect(fieldSizeDisplay, 7.0f, 6.2f, 20.0f, 4.17f);
        setVertexRect(message, 7.0f, 13.2f, 20.0f, 8.0f);

        float x = 7.0f; float y = 24.2f;
        float width = 20.0f; float height = 4.17f;
        float yInterval = 4.2f; float yGroupInterval = 0.4f;
        setVertexRect(resumeGameButton, x, y, width, height);
        y += yInterval;
        setVertexRect(newGameButton, x, y, width, height);
        y += yInterval;
        y += yGroupInterval;
        setVertexRect(backButton, x, y, width, height);
    }
}
