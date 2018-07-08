package com.yucatio.tetmas.winlossrecord.view;

import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.view.ScreenLayout;

import java.util.EnumMap;

public class WinLossRecordLayout extends ScreenLayout {
    public static float[] background = new float[12];
    public static float[] title = new float[12];
    public static float[] fieldSizeDisplay = new float[12];
    public static float[] winLossRecordBox = new float[12];

    public static EnumMap<Stage, float[][]> winCountMap = new EnumMap<>(Stage.class);
    public static EnumMap<Stage, float[][]> evenCountMap = new EnumMap<>(Stage.class);

    public static float[] previousButton = new float[12];
    public static float[] nextButton = new float[12];
    public static float[] backButton = new float[12];

    static {
        setVertexRect(background, 0.0f, 0.0f, 34.0f, 40.0f);

        setVertexRect(title, 7.0f, 2.0f, 20.0f, 4.17f);
        setVertexRect(fieldSizeDisplay, 7.0f, 6.2f, 20.0f, 4.17f);

        setVertexRect(winLossRecordBox, 6.5f, 11.1f, 21.0f, 18.0f);

        Stage[] stages = {Stage.EASY, Stage.MIDDLE, Stage.HARD};
        float width = 0.825f; float height = 1.2f;
        float y = 12.5f;
        float xInterval = -width; float yInterval = 6.0f;
        for (Stage stage : stages) {
            float x = 25.2f;
            float[][] digitVertexes = new float[4][];
            for (int i=0; i<4; i++) {
                float[] vertexes = new float[12];
                setVertexRect(vertexes, x, y, width, height);
                digitVertexes[i] = vertexes;
                x += xInterval;
            }
            winCountMap.put(stage, digitVertexes);
            y += yInterval;
        }

        y = 14.55f;
        for (Stage stage : stages) {
            float x = 25.2f;
            float[][] digitVertexes = new float[4][];
            for (int i=0; i<4; i++) {
                float[] vertexes = new float[12];
                setVertexRect(vertexes, x, y, width, height);
                digitVertexes[i] = vertexes;
                x += xInterval;
            }
            evenCountMap.put(stage, digitVertexes);
            y += yInterval;
        }


        float x = 7.0f; y = 30.0f;
        width = 10.0f; height = 4.17f;
        xInterval = 10.0f; yInterval = 4.2f;
        setVertexRect(previousButton, x, y, width, height);
        setVertexRect(nextButton, x+xInterval, y, width, height);
        y += yInterval;
        width = 20.0f; height = 4.17f;
        setVertexRect(backButton, x, y, width, height);

    }
}
