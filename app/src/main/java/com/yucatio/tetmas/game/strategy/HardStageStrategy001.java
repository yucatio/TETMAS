package com.yucatio.tetmas.game.strategy;

import android.util.Log;

import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.element.CellState;
import com.yucatio.tetmas.game.element.GameField;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;
import com.yucatio.tetmas.util.Const;

import java.io.Serializable;
import java.util.EnumMap;

public class HardStageStrategy001 implements Strategy, Serializable {
    private static final String TAG = "HardStageStrategy001";

    private static final float OPENING_THRESHOLD = 0.8f;
    private static final float ENDING_THRESHOLD = 0.2f;

    private Strategy openingStrategy;
    private EvaluateFunctionStrategy05 middleStrategy;
    private Strategy endingStrategy;

    private int initialEvaluateCellNum;
    private int maxEvaluateCellNum;

    public HardStageStrategy001(FieldSize fieldSize) {
        switch (fieldSize) {
            case FIFTEEN_SQUARE:
                initialEvaluateCellNum = 10;
                maxEvaluateCellNum = 15;
                break;
            case TWENTY_SQUARE:
                initialEvaluateCellNum = 20;
                maxEvaluateCellNum = 25;
        }

        openingStrategy = new NonStickingStrategy();
        middleStrategy = new EvaluateFunctionStrategy06(initialEvaluateCellNum);
        endingStrategy = new EvaluateFunctionStrategy06(maxEvaluateCellNum);

    }

    @Override
    public TetrominoPosition getTetrominoPosition(GameField field, CellState state, CellState stateAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother) {
        float emptyRatio = (field.getEmptyCellNum() * 1.0f) / (field.getGameWidth() * field.getGameHeight());
        Strategy strategy;

        if (emptyRatio > OPENING_THRESHOLD) {
            if (Const.DEBUG_ENABLED) {
                Log.v(TAG, "openingStrategy");
            }
            strategy = openingStrategy;
        } else if (emptyRatio > ENDING_THRESHOLD) {
            if (Const.DEBUG_ENABLED) {
                Log.v(TAG, "middleStrategy");
            }
            int evaluateCellNum = (int)((-(maxEvaluateCellNum - initialEvaluateCellNum)*emptyRatio + maxEvaluateCellNum * OPENING_THRESHOLD - initialEvaluateCellNum * ENDING_THRESHOLD)/(OPENING_THRESHOLD - ENDING_THRESHOLD));
            middleStrategy.setEvaluateCellNum(evaluateCellNum);
            strategy = middleStrategy;
        } else {
            if (Const.DEBUG_ENABLED) {
                Log.v(TAG, "endStrategy");
            }
            strategy = endingStrategy;
        }

        return strategy.getTetrominoPosition(field, state, stateAnother, availableTetromino, availableTetrominoAnother);
    }
}
