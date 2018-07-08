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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class EvaluateFunctionStrategy05 implements Strategy, Serializable {
    private static final String TAG = "EvalFuncStrategy05";

    private Random random = new Random();
    private RandomPutStrategy randomPutStrategy = new RandomPutStrategy();

    // 評価するcell数
    private int evaluateCellNum;

    // テトロミノ数調整数
    private EnumMap<Tetromino, Float> tetrominoNumAdjustmentValue = new EnumMap<>(Tetromino.class);

    public EvaluateFunctionStrategy05(int evaluateCellNum) {
        this.evaluateCellNum = evaluateCellNum;
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
        float ratio = (evaluateCellNum * 1.0f) / (emptyCellNum * 1.0f);

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
                        float value = surroundedValue + tetrominoValue;

                        if (surroundedSelf && ! isFirst) {
                            // 自テトロミノに囲まれている
                            value += evaluateTerritoryAcquisition(tetrominoPosition, pointSet, field, state, availableTetrominoAnother, totalTetrominoAnother);
                        } else if (!isIsolated(tetrominoPosition, field, state)){
                            // 壁または自テトロミノに1カ所以上接している
                            value += evaluateTerritoryAcquisitionMoreThanOneNeighbor(tetrominoPosition, pointSet, field, state, availableTetrominoAnother, totalTetrominoAnother);
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

    protected void setTetrominoNumAdjustmentValue(Map<Tetromino, Integer> availableTetromino) {
        float coefficient = 0.1f;

        int total = getTotalTetromino(availableTetromino);
        int availableNum = 0;
        // 使用できるテトロミノの数
        for (Integer i : availableTetromino.values()) {
            if (i > 0) {
                availableNum++;
            }
        }

        float average = (total * 1.0f) / (availableNum * 1.0f);

        for (Map.Entry<Tetromino, Integer> entry : availableTetromino.entrySet()) {
            tetrominoNumAdjustmentValue.put(entry.getKey(), coefficient * (entry.getValue() - average));
        }
    }

    protected float evaluateTetrominoNumAdjustment(Tetromino tetromino) {
        return tetrominoNumAdjustmentValue.get(tetromino);
    }

    /**
     * pointSetがstateおよびwallで囲まれているかどうか返します
     * @param field ゲームフィールド
     * @param pointSet 判定対象のemptyCluster
     * @param state プレイヤーのCellState
     * @return pointSetがpointSetがstateおよびwallで囲まれている場合true, そうでない場合はfalse
     */
    protected boolean checkSurrounded(GameField field, Set<Point> pointSet, CellState state) {
        // setをコピー
        Set<Point> pointSetCopy = new HashSet<>(pointSet);

        Set<Point> newCluster = new HashSet<>();
        Point point = pointSetCopy.iterator().next();

        return field.findCellCluster(point, pointSetCopy, newCluster, state);
    }

    /**
     * 与えられたtetrominoPositionのまわり(8近傍)に自テトロミノが存在しないか(孤立しているか)を返します
     * @param tetrominoPosition 判定対象のtetrominoPosition
     * @param field ゲームフィールド
     * @param state 自テトロミノのstate
     * @return 与えられたtetrominoPositionのまわり(8近傍)にテトロミノや壁が存在しない場合true、存在する場合false
     */
    protected boolean isIsolated(TetrominoPosition tetrominoPosition, GameField field, CellState state) {
        for (Point point : tetrominoPosition.getTetromino().getEightNeighbors(tetrominoPosition.getSpin())) {
            int x = tetrominoPosition.getX() + point.x;
            int y = tetrominoPosition.getY() + point.y;

            if (field.getCellState(x, y) == CellState.WALL || field.getCellState(x, y) == state) {
                return false;
            }
        }

        return true;
    }


    protected float evaluateTerritoryAcquisition(TetrominoPosition tPosition, Set<Point> pointSet, GameField field, CellState state, Map<Tetromino, Integer> availableTetrominoAnother, int totalTetrominoAnother) {
        float value = 0.0f;

        // setをコピー
        Set<Point> pointSetCopy = new HashSet<>(pointSet);

        // 置かれる部分のpointを削除
        for (Point point : tPosition.getTetromino().getTetrominoPoints(tPosition.getSpin())) {
            int x = tPosition.getX() + point.x;
            int y = tPosition.getY() + point.y;

            pointSetCopy.remove(field.getPointPool(x, y));

        }

        while(!pointSetCopy.isEmpty()) {

            // 新しいemptyCellClusterが自テトロミノで囲まれていたら、スコアを計算
            Point point = pointSetCopy.iterator().next();
            Set<Point> newCluster = new HashSet<>();

            if (field.findCellCluster(point, pointSetCopy, newCluster, state)) {
                // 自テトロミノで囲まれている

                // 相手が置けるテトロミノの数
                int placeableTetrominoNum = 0;

                for (Map.Entry<Tetromino, Integer> entry : availableTetrominoAnother.entrySet()) {
                    Tetromino tetromino = entry.getKey();

                    if (field.hasSpace(newCluster, tetromino)) {
                        placeableTetrominoNum += entry.getValue();
                    }

                }

                value += newCluster.size() * (1.0f - (placeableTetrominoNum * 1.0f)/(totalTetrominoAnother *1.0f));
            }
        }

        return value;

    }

    protected float evaluateTerritoryAcquisitionMoreThanOneNeighbor(TetrominoPosition tPosition, Set<Point> pointSet, GameField field, CellState state, Map<Tetromino, Integer> availableTetrominoAnother, int totalTetromino) {
        float value = 0.0f;

        // setをコピー
        Set<Point> pointSetCopy = new HashSet<>(pointSet);

        // 置かれる部分のpointを削除
        for (Point point : tPosition.getTetromino().getTetrominoPoints(tPosition.getSpin())) {
            int x = tPosition.getX() + point.x;
            int y = tPosition.getY() + point.y;

            pointSetCopy.remove(field.getPointPool(x, y));

        }

        // 始点を探す
        int startIndex = 0;
        Point[] eightNeighbors = tPosition.getTetromino().getEightNeighbors(tPosition.getSpin());
        for (int i = 0; i < eightNeighbors.length; i++) {
            int x = tPosition.getX() + eightNeighbors[i].x;
            int y = tPosition.getY() + eightNeighbors[i].y;

            if (field.getCellState(x, y) != CellState.EMPTY) {
                startIndex = i;
                break;
            }
        }

        CellState startState = null;
        boolean hasPoint = false;
        for (int i = 0; i < eightNeighbors.length + 1; i++) {
            int index = (i + startIndex) % eightNeighbors.length;

            int x = tPosition.getX() + eightNeighbors[index].x;
            int y = tPosition.getY() + eightNeighbors[index].y;

            if (field.getCellState(x, y) == CellState.EMPTY) {
                if (pointSetCopy.contains(field.getPointPool(x, y))) {
                    hasPoint = true;
                }
            } else {
                if (hasPoint) {
                    int previousIndex = (index - 1 + eightNeighbors.length) % eightNeighbors.length;
                    Point point = field.getPointPool(tPosition.getX() + eightNeighbors[previousIndex].x, tPosition.getY() + eightNeighbors[previousIndex].y);
                    Set<Point> newCluster = new HashSet<>();
                    if ((startState == CellState.WALL || startState == state) && (field.getCellState(x, y) == CellState.WALL || field.getCellState(x, y) == state)) {
                        // 囲まれている可能性がある
                        // チェックする
                        if (field.findCellCluster(point, pointSetCopy, newCluster, state)) {
                            // 自テトロミノで囲まれている

                            // 相手が置けるテトロミノの数
                            int placeableTetrominoNum = 0;

                            for (Map.Entry<Tetromino, Integer> entry : availableTetrominoAnother.entrySet()) {
                                Tetromino tetromino = entry.getKey();

                                if (field.hasSpace(newCluster, tetromino)) {
                                    placeableTetrominoNum += entry.getValue();
                                }

                            }

                            value += newCluster.size() * (1.0f - (placeableTetrominoNum * 1.0f)/(totalTetromino *1.0f));
                        }

                    }

                    // リセット
                    hasPoint = false;
                    startState = field.getCellState(x, y);
                } else {
                    // 途中にemptyがない
                    startState = field.getCellState(x, y);
                }
            }

        }

        for (Point neighborPoint : tPosition.getTetromino().getEightNeighbors(tPosition.getSpin())) {
            int x = tPosition.getX() + neighborPoint.x;
            int y = tPosition.getY() + neighborPoint.y;

            Point point = new Point(x, y);
            if (! pointSetCopy.contains(point)) {
                continue;
            }
            Set<Point> newCluster = new HashSet<>();

            if (field.findCellCluster(point, pointSetCopy, newCluster, state)) {
                // 自テトロミノで囲まれている

                // 相手が置けるテトロミノの数
                int placeableTetrominoNum = 0;

                for (Map.Entry<Tetromino, Integer> entry : availableTetrominoAnother.entrySet()) {
                    Tetromino tetromino = entry.getKey();

                    if (field.hasSpace(newCluster, tetromino)) {
                        placeableTetrominoNum += entry.getValue();
                    }

                }

                value += newCluster.size() * (1.0f - (placeableTetrominoNum * 1.0f)/(totalTetromino *1.0f));
            }
        }

        return value;

    }

    protected float evaluateSurroundedByAnother(Set<Point> pointSet, GameField field, Map<Tetromino, Integer> availableTetromino, int totalTetromino) {
        float value = 0.0f;

        // 入れる数が少ない場合にスコアが高くなる。エリアが大きいほどスコアが高くなる

        // 自分が置けるテトロミノの数
        int placeableTetrominoNum = 0;

        for (Map.Entry<Tetromino, Integer> entry : availableTetromino.entrySet()) {
            Tetromino tetromino = entry.getKey();

            if (field.hasSpace(pointSet, tetromino)) {
                placeableTetrominoNum += entry.getValue();
            }

        }

        value += pointSet.size() * (1.0f - (placeableTetrominoNum * 1.0f)/(totalTetromino *1.0f));

        return value;
    }

    protected int getTotalTetromino(Map<Tetromino, Integer> availableTetrominoMap) {
        int total = 0;
        for (Integer i : availableTetrominoMap.values()) {
            total += i;
        }

        return total;
    }

    public void setEvaluateCellNum(int evaluateCellNum) {
        this.evaluateCellNum = evaluateCellNum;
    }

    public int getEvaluateCellNum() {
        return evaluateCellNum;
    }

}


