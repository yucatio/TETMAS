package com.yucatio.tetmas.gameend.view;

import com.yucatio.tetmas.view.ScreenLayout;

public class GameEndLayout extends ScreenLayout {
    public static float[] backScreen = new float[12];

    public static float[] title = new float[12];
    public static float[] stage = new float[12];

    public static float[] outcome = new float[12];
    public static float[] winLossRecord = new float[12];

    public static float[] outcomeTwoPlayer = new float[12];

    public static float[][] winCount = new float[4][];
    public static float[][] evenCount = new float[4][];

    public static float[] newGameButton = new float[12];
    public static float[] backButton = new float[12];

    static {
        setVertexRect(backScreen, 2.4f, 10.4f, 19.2f, 19.2f);

        setVertexRect(title, 7.0f, 1.5f, 20.0f, 4.17f);
        setVertexRect(stage, 7.0f, 5.7f, 20.0f, 4.17f);

        setVertexRect(outcome, 3.0f, 12.5f, 18.0f, 6.0f);

        setVertexRect(winLossRecord, 5.0f, 21.0f, 14.0f, 7.0f);

        setVertexRect(outcomeTwoPlayer, 3.0f, 17.0f, 18.0f, 6.0f);

        float width = 0.75f; float height = 1.5f;
        float xInterval = -width;
        float x = 16.0f; float y = 24.1f;
        for (int i = 0; i < 4; i++) {
            float[] vertexes = new float[12];
            setVertexRect(vertexes, x, y, width, height);
            x += xInterval;
            winCount[i] = vertexes;
        }

        x = 16.0f; y = 26.3f;
        for (int i = 0; i < 4; i++) {
            float[] vertexes = new float[12];
            setVertexRect(vertexes, x, y, width, height);
            x += xInterval;
            evenCount[i] = vertexes;
        }

        x = 7.0f; y = 30.0f;
        width = 20.0f; height = 4.17f;
        float yInterval = 4.2f;
        setVertexRect(newGameButton, x, y, width, height);
        y += yInterval;
        setVertexRect(backButton, x, y, width, height);

    }
}
