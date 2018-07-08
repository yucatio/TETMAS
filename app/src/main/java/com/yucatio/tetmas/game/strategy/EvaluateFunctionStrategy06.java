package com.yucatio.tetmas.game.strategy;

import android.util.Log;

import com.yucatio.tetmas.game.element.CellState;
import com.yucatio.tetmas.game.element.GameField;
import com.yucatio.tetmas.game.element.Point;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;
import com.yucatio.tetmas.util.Const;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class EvaluateFunctionStrategy06 extends EvaluateFunctionStrategy05 implements Strategy, Serializable {
    private static final String TAG = "EvalFuncStrategy06";
    private Random random = new Random();
    private RandomPutStrategy randomPutStrategy = new RandomPutStrategy();

    public EvaluateFunctionStrategy06(int evaluateCellNum) {
        super(evaluateCellNum);
    }

    @Override
    public TetrominoPosition getTetrominoPosition(GameField field, CellState state, CellState stateAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother) {
        // 最高評価値
        float maxValue = 0.0f;
        // 最高評価値をもつ手の集合
        List<TetrominoPosition> maxValuePositions = new ArrayList<>();

        // 空きセル数数える
        int emptyCellNum = field.getEmptyCellNum();
        // 評価するセルの割合を出す。空きセル数より評価するセル数が多ければすべて評価する
        float ratio = (getEvaluateCellNum() * 1.0f) / (emptyCellNum * 1.0f);

        setTetrominoNumAdjustmentValue(availableTetromino);
        // 自分が持っているテトロミノの数
        int totalTetromino = getTotalTetromino(availableTetromino);
        // 相手が持っているテトロミノの数
        int totalTetrominoAnother = getTotalTetromino(availableTetrominoAnother);
        // 初回フラグ
        boolean isFirst = (field.getEmptyCellClusterSet().iterator().next().size() == field.getGameWidth() * field.getGameHeight());

        for (Set<Point> pointSet : field.getEmptyCellClusterSet()) {
            // 相手に囲まれている領域か
            boolean surroundedSelf = checkSurrounded(field, pointSet, state);
            boolean surroundedAnother = checkSurrounded(field, pointSet, stateAnother);
            float surroundedValue = 0.0f;

            if (surroundedAnother) {
                // 相手に囲まれている
                surroundedValue += evaluateSurroundedByAnother(pointSet, field, availableTetromino, totalTetromino);
            }

            for (Point point : pointSet) {
                if (Math.random() > ratio) {
                    continue;
                }

                // foreach tetromino
                for (Map.Entry<Tetromino, Integer> entry : availableTetromino.entrySet()) {
                    if (entry.getValue() <= 0) {
                        continue;
                    }

                    Tetromino tetromino = entry.getKey();

                    float tetrominoValue = evaluateTetrominoNumAdjustment(tetromino);

                    // foreach spin
                    for (int spin = 0; spin < tetromino.getSpinNum(); spin++) {
                        Point[] tetrominoPoints = tetromino.getTetrominoPoints(spin);
                        int offsetX = tetrominoPoints[0].x;
                        int offsetY = tetrominoPoints[0].y;
                        TetrominoPosition tetrominoPosition = new TetrominoPosition(tetromino, point.x - offsetX, point.y - offsetY, spin);

                        if (! field.canPlaceTetromino(tetrominoPosition)) {
                            // 置けなかったら次
                            continue;
                        }

                        // 置ける
                        float value = 0.0f;
                        value += surroundedValue;
                        value += tetrominoValue;

                        // 壁または自テトロミノに接しすぎているとマイナス
                        value += evaluateContact(tetrominoPosition, field, state);

                        if (surroundedSelf && ! isFirst) {
                            // 自テトロミノに囲まれている
                            value += evaluateTerritoryAcquisition(tetrominoPosition, pointSet, field, state, availableTetrominoAnother, totalTetrominoAnother);
                        } else if (!isIsolated(tetrominoPosition, field, state)){
                            // 壁または自テトロミノに1カ所以上接している
                            value += evaluateTerritoryAcquisitionMoreThanOneNeighbor(tetrominoPosition, pointSet, field, state, availableTetrominoAnother, totalTetrominoAnother);
                        }

                        // 相手の邪魔をしているか
                        float interruptCoefficient = 0.3f;
                        if (surroundedAnother && !isFirst) {
                            // 相手のテトロミノに囲まれている
                            value += interruptCoefficient * evaluateTerritoryAcquisition(tetrominoPosition, pointSet, field, stateAnother, availableTetromino, totalTetromino);
                        } else if (!isIsolated(tetrominoPosition, field, stateAnother)) {
                            // 壁または自テトロミノに1カ所以上接している
                            value += interruptCoefficient * evaluateTerritoryAcquisitionMoreThanOneNeighbor(tetrominoPosition, pointSet, field, stateAnother, availableTetromino, totalTetromino);
                        }

                        if (Const.DEBUG_ENABLED) {
                            Log.v(TAG, "value:" + value + ", " + point + "," + tetrominoPosition);
                        }

                        if (Math.abs(maxValue - value) < 0.0001f) {
                            // 同じくらいの評価
                            maxValuePositions.add(tetrominoPosition);
                        } else if (value > maxValue) {
                            maxValue = value;
                            maxValuePositions.clear();
                            maxValuePositions.add(tetrominoPosition);
                        }
                    }
                }

            }
        }

        if (maxValuePositions.size() <=0) {
            Log.i(TAG, "no maxValuePosition. return random TetrominoPosition.");
            return randomPutStrategy.getTetrominoPosition(field, state, stateAnother, availableTetromino, availableTetrominoAnother);
        }

        int index = random.nextInt(maxValuePositions.size());

        return maxValuePositions.get(index);
    }

    private float evaluateContact(TetrominoPosition tetrominoPosition, GameField field, CellState state) {
        float coefficient = -0.1f;
        int contactNum = 0;

        for (Point point : tetrominoPosition.getTetromino().getFourNeighbors(tetrominoPosition.getSpin())) {
            int x = tetrominoPosition.getX() + point.x;
            int y = tetrominoPosition.getY() + point.y;

            if (field.getCellState(x, y) == CellState.WALL || field.getCellState(x, y) == state) {
                contactNum++;
            }
        }

        if (contactNum <= 2) {
            return 0.0f;
        }

        return coefficient * contactNum;

    }

}
