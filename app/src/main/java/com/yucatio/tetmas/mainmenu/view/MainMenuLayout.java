package com.yucatio.tetmas.mainmenu.view;

import com.yucatio.tetmas.view.ScreenLayout;

public class MainMenuLayout extends ScreenLayout {
    public static float[] background = new float[12];
    public static float[] title = new float[12];
    public static float[] easyLevel = new float[12];
    public static float[] middleLevel = new float[12];
    public static float[] hardLevel = new float[12];
    public static float[] twoPlayer = new float[12];
    public static float[] winLossRecords = new float[12];
    public static float[] help = new float[12];

    static {
        setVertexRect(background, 0.0f, 0.0f, 34.0f, 40.0f);

        setVertexRect(title, 4.0f, 4.0f, 26.0f, 6.5f);

        float x = 7.0f; float y = 12.0f;
        float width = 20.0f; float height = 4.17f;
        float yInterval = 4.2f; float yGroupInterval = 0.4f;
        setVertexRect(easyLevel, x, y, width, height);
        y += yInterval;
        setVertexRect(middleLevel, x, y, width, height);
        y += yInterval;
        setVertexRect(hardLevel, x, y, width, height);
        y += yInterval;
        y += yGroupInterval;
        setVertexRect(twoPlayer, x, y, width, height);
        y += yInterval;
        y += yGroupInterval;
        setVertexRect(winLossRecords, x, y, width, height);
        y += yInterval;
        y += yGroupInterval;
        setVertexRect(help, x, y, width, height);

    }
}
