package com.yucatio.tetmas.help.view;

import com.yucatio.tetmas.view.ScreenLayout;

public class HelpLayout extends ScreenLayout {
    public static float[] background = new float[12];
    public static float[] title = new float[12];
    public static float[] description = new float[12];
    public static float[] previousButton = new float[12];
    public static float[] nextButton = new float[12];
    public static float[] backButton = new float[12];

    static {
        setVertexRect(background, 0.0f, 0.0f, 34.0f, 40.0f);

        setVertexRect(title, 7.0f, 2.0f, 20.0f, 4.17f);

        setVertexRect(description, 7.0f, 6.5f, 20.13f, 23.0f);

        float x = 7.0f; float y = 30.0f;
        float width = 10.0f; float height = 4.17f;
        float xInterval = 10.0f; float yInterval = 4.2f;
        setVertexRect(previousButton, x, y, width, height);
        setVertexRect(nextButton, x+xInterval, y, width, height);
        y += yInterval;
        width = 20.0f; height = 4.17f;
        setVertexRect(backButton, x, y, width, height);
    }
}
