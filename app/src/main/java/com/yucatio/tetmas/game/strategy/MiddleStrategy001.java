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

public class MiddleStrategy001 implements Strategy, Serializable {
    private static final String TAG = "MiddleStrategy001";
    private NonStickingStrategy nonStickingStrategy;
    private EvaluateFunctionStrategy05 evalFunc05;

    private float randomRatio;
    private int initialEvaluateCellNum;
    private int maxEvaluateCellNum;


    public MiddleStrategy001(FieldSize fieldSize) {
        nonStickingStrategy = new NonStickingStrategy();

        if (fieldSize == FieldSize.FIFTEEN_SQUARE) {
            randomRatio = 0.3f;
            initialEvaluateCellNum = 4;
            maxEvaluateCellNum = 15;
        } else {
            randomRatio = 0.2f;
            initialEvaluateCellNum = 10;
            maxEvaluateCellNum = 21;
        }

        evalFunc05 = new EvaluateFunctionStrategy05(initialEvaluateCellNum);

    }

    @Override
    public TetrominoPosition getTetrominoPosition(GameField field, CellState state, CellState stateAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother) {
        if (Math.random() < randomRatio) {
            if (Const.DEBUG_ENABLED) {
                Log.v(TAG, "NonStickingStrategy used.");
            }
            return  nonStickingStrategy.getTetrominoPosition(field, state, stateAnother, availableTetromino, availableTetrominoAnother);
        } else {
            if (Const.DEBUG_ENABLED) {
                Log.v(TAG, "EvaluateFunctionStrategy05 used.");
            }
            float emptyRatio = (field.getEmptyCellNum() * 1.0f) / (field.getGameWidth() * field.getGameHeight());
            evalFunc05.setEvaluateCellNum((int)((maxEvaluateCellNum - initialEvaluateCellNum)*(1-emptyRatio) + initialEvaluateCellNum));
            return evalFunc05.getTetrominoPosition(field, state, stateAnother, availableTetromino, availableTetrominoAnother);
        }
    }
}
