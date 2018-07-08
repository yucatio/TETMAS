package com.yucatio.tetmas.game.view;

import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.element.Direction;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.texture.VertexAnimation;
import com.yucatio.tetmas.view.ScreenLayout;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class GameWorldLayout extends ScreenLayout {
    public static final float gameWorldWidth = 34.0f;
    public static final float gameWorldHeight = 40.0f;

    public static final float gameWorldOffsetX = 0.0f;
    public static final float gameWorldOffsetY = 0.0f;

    public static final EnumMap<FieldSize, Float> fieldOffsetXMap = new EnumMap<>(FieldSize.class);
    public static final EnumMap<FieldSize, Float> fieldOffsetYMap = new EnumMap<>(FieldSize.class);

    public static float[] background = new float[12];

    public static float[] tetmasField;
    public static EnumMap<FieldSize, Float> cellWidthMap = new EnumMap<>(FieldSize.class);

    public static float[] tetrimino;
    public static float[] cell;
    public static EnumMap<Tetromino, Integer> activeTetrominoOffsetX = new EnumMap<>(Tetromino.class);
    public static List<Integer> activeTetrominoOffsetY = new ArrayList<>();

    public static VertexAnimation cellStateChangeAnimation;
    public static float changeCellDelay = 0.3f;

    public static List<EnumMap<Tetromino, float[]>> tetrominoButtonMapList = new ArrayList<>();
    public static List<float[]> cancelButtonList = new ArrayList<>();
    public static List<EnumMap<Tetromino, float[]>> tetrominoNum = new ArrayList<>();
    public static List<float[]> scoreRegionList = new ArrayList<>();
    public static List<float[][]> scoreNumberList = new ArrayList<>();
    public static float[] okButton;
    public static EnumMap<Direction, float[]> arrowKeyMapList = new EnumMap<>(Direction.class);
    public static float[] spinKey;

    public static float[] gameMessage = new float[12];

    static {
        fieldOffsetXMap.put(FieldSize.FIFTEEN_SQUARE, 2.16f);
        fieldOffsetXMap.put(FieldSize.TWENTY_SQUARE, 2.0f);

        fieldOffsetYMap.put(FieldSize.FIFTEEN_SQUARE, 10.16f);
        fieldOffsetYMap.put(FieldSize.TWENTY_SQUARE, 10.0f);

        setVertexRect(background, 0.0f, 0.0f, 34.0f, 40.0f);

        float width = 21.0f; float height = 21.0f;
        float x = 1.5f; float y= 9.5f;
        tetmasField = new float[12];
        setVertexRect(tetmasField, x, y, width, height);

        cellWidthMap.put(FieldSize.FIFTEEN_SQUARE, 1.31f);
        cellWidthMap.put(FieldSize.TWENTY_SQUARE, 1.0f);

        width = 4.0f; height = 4.0f;
        x = 0.0f; y = 0.0f;
        tetrimino = new float[12];
        setVertexRect(tetrimino, x, y, width, height);
        width = 1.0f; height = 1.0f;
        cell = new float[12];
        setVertexRect(cell, x, y, width, height);

        activeTetrominoOffsetX.put(Tetromino.I, -1);
        activeTetrominoOffsetX.put(Tetromino.O, 2);
        activeTetrominoOffsetX.put(Tetromino.T, 5);
        activeTetrominoOffsetX.put(Tetromino.J, 8);
        activeTetrominoOffsetX.put(Tetromino.L, 11);
        activeTetrominoOffsetX.put(Tetromino.S, 14);
        activeTetrominoOffsetX.put(Tetromino.Z, 17);

        activeTetrominoOffsetY.add(-1);
        activeTetrominoOffsetY.add(17);

        float[] vertex0 = new float[12];
        setVertexRect(vertex0, 0.4f, 0.4f, 0.2f, 0.2f);
        float[] vertex1 = new float[12];
        setVertexRect(vertex1, -0.2f, -0.2f, 1.4f, 1.4f);
        float[] vertex2 = new float[12];
        setVertexRect(vertex2, 0.0f, 0.0f, 1.0f, 1.0f);
        cellStateChangeAnimation = new VertexAnimation(
                new VertexAnimation.KeyFrame(0.0f, vertex0),
                new VertexAnimation.KeyFrame(0.4f, vertex1),
                new VertexAnimation.KeyFrame(0.6f, vertex2)
        );


        Tetromino[] tetrominoArray = {Tetromino.I, Tetromino.O, Tetromino.T, Tetromino.J, Tetromino.L, Tetromino.S, Tetromino.Z};

        width = 2.89f; height = 4.5f;
        float xInterval = 3.5f;
        x = 2.0f; y = 1.5f;

        EnumMap<Tetromino, float[]> vertexEnumMap = new EnumMap<>(Tetromino.class);
        for (Tetromino tetromino : tetrominoArray) {
            float[] vertexes = new float[12];
            setVertexRect(vertexes, x, y, width, height);
            vertexEnumMap.put(tetromino, vertexes);

            x += xInterval;
        }
        tetrominoButtonMapList.add(vertexEnumMap);

        x = 2.0f; y = 31.5f;
        vertexEnumMap = new EnumMap<>(Tetromino.class);
        for (Tetromino tetromino : tetrominoArray) {
            float[] vertexes = new float[12];
            setVertexRect(vertexes, x, y, width, height);
            vertexEnumMap.put(tetromino, vertexes);

            x += xInterval;
        }
        tetrominoButtonMapList.add(vertexEnumMap);


        float[] vertexes = new float[12];
        x = 27.0f; y = 1.5f;
        setVertexRect(vertexes, x, y, width, height);
        cancelButtonList.add(vertexes);
        vertexes = new float[12];
        y = 31.5f;
        setVertexRect(vertexes, x, y, width, height);
        cancelButtonList.add(vertexes);

        width = 2.5f; height = 2.5f;
        xInterval = 3.5f;
        x = 2.2f; y = 6.3f;
        vertexEnumMap = new EnumMap<>(Tetromino.class);
        for (Tetromino tetromino : tetrominoArray) {
            vertexes = new float[12];
            setVertexRect(vertexes, x, y, width, height);
            vertexEnumMap.put(tetromino, vertexes);

            x += xInterval;
        }
        tetrominoNum.add(vertexEnumMap);

        x = 2.2f; y = 36.3f;
        vertexEnumMap = new EnumMap<>(Tetromino.class);
        for (Tetromino tetromino : tetrominoArray) {
            vertexes = new float[12];
            setVertexRect(vertexes, x, y, width, height);
            vertexEnumMap.put(tetromino, vertexes);

            x += xInterval;
        }
        tetrominoNum.add(vertexEnumMap);

        vertexes = new float[12];
        width = 3.8f; height = 2.85f;
        x = 24.0f; y = 14.0f;
        setVertexRect(vertexes, x, y, width, height);
        scoreRegionList.add(vertexes);
        vertexes = new float[12];
        x = 28.2f;
        setVertexRect(vertexes, x, y, width, height);
        scoreRegionList.add(vertexes);

        width = 0.8f; height = 1.16f;
        xInterval = -width;
        x = 26.3f; y = 14.85f;
        scoreNumberList.add(new float[3][]);
        for (int i = 0; i < 3; i++) {
            vertexes = new float[12];
            setVertexRect(vertexes, x, y, width, height);
            x += xInterval;
            scoreNumberList.get(0)[i] = vertexes;
        }
        x = 30.5f;
        scoreNumberList.add(new float[3][]);
        for (int i = 0; i < 3; i++) {
            vertexes = new float[12];
            setVertexRect(vertexes, x, y, width, height);
            x += xInterval;
            scoreNumberList.get(1)[i] = vertexes;
        }


        width = 8.0f; height = 3.27f;
        x = 24.0f; y = 10.0f;
        okButton = new float[12];
        setVertexRect(okButton, x, y, width, height);

        width = 4.0f; height = 2.58f;
        vertexes = new float[12];
        x = 26.0f; y = 18.0f;
        setVertexRect(vertexes, x, y, width, height);
        arrowKeyMapList.put(Direction.TOP, vertexes);
        vertexes = new float[12];
        x = 26.0f; y = 23.2f;
        setVertexRect(vertexes, x, y, width, height);
        arrowKeyMapList.put(Direction.BOTTOM, vertexes);
        vertexes = new float[12];
        x = 28.0f; y = 20.6f;
        setVertexRect(vertexes, x, y, width, height);
        arrowKeyMapList.put(Direction.RIGHT, vertexes);
        vertexes = new float[12];
        x = 24.0f; y = 20.6f;
        setVertexRect(vertexes, x, y, width, height);
        arrowKeyMapList.put(Direction.LEFT, vertexes);

        width = 3.7f; height = 2.58f;
        x = 26.15f; y = 26.5f;
        spinKey = new float[12];
        setVertexRect(spinKey, x, y, width, height);

        setVertexRect(gameMessage, 2.0f, 15.0f, 20.0f, 10.0f);

    }
}
