package com.yucatio.tetmas.game.strategy;

import com.yucatio.tetmas.game.element.CellState;
import com.yucatio.tetmas.game.element.GameField;
import com.yucatio.tetmas.game.element.Point;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class NonStickingStrategy implements Strategy, Serializable {
    private static final String TAG = "NonStickingStrategy";
    private List<Tetromino> tetrominoList = Arrays.asList(Tetromino.values());
    private Random random = new Random();

    @Override
    public TetrominoPosition getTetrominoPosition(GameField field, CellState state, CellState stateAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother) {
        int nextX = random.nextInt(field.getGameWidth());
        int nextY = random.nextInt(field.getGameHeight());

        Collections.shuffle(tetrominoList);

        TetrominoPosition lastPosition = null;

        // ランダムに置けるところを探します
        for (int i=0; i < field.getGameHeight(); i++) {
            nextX = (nextX + 1) % field.getGameHeight();
            for (int j=0; j < field.getGameWidth(); j++) {
                nextY = (nextY + 1) % field.getGameWidth();

                if (field.getCellState(nextX, nextY) != CellState.EMPTY) {
                    continue;
                }

                Point point = field.getPointPool(nextX, nextY);

                // foreach テトロミノ
                for (Tetromino tetromino : tetrominoList) {
                    if (availableTetromino.get(tetromino) <= 0) {
                        continue;
                    }

                    int nextSpin = random.nextInt(tetromino.getSpinNum());

                    // foreach spin
                    for (int k = 0; k < tetromino.getSpinNum(); k++) {
                        nextSpin = (nextSpin + 1) % tetromino.getSpinNum();
                        Point[] tetrominoPoints = tetromino.getTetrominoPoints(nextSpin);
                        int offsetX = tetrominoPoints[0].x;
                        int offsetY = tetrominoPoints[0].y;
                        TetrominoPosition tetrominoPosition = new TetrominoPosition(tetromino, point.x - offsetX, point.y - offsetY, nextSpin);
                        if (field.canPlaceTetromino(tetrominoPosition)) {
                            lastPosition = tetrominoPosition;

                            if (hasSelfNeighborLessThanOrEqual(tetrominoPosition, field, state,2)) {
                                // 置けるかつ隣接している箇所が2カ所以下
                                return tetrominoPosition;
                            }
                        }

                    }
                }
            }
        }

        // すべて3カ所以上置ける場合は最後に発見した置ける場所を返す
        return lastPosition;
    }

    private boolean hasSelfNeighborLessThanOrEqual(TetrominoPosition tetrominoPosition, GameField field, CellState state, int threshold) {
        int count = 0;

        for (Point point : tetrominoPosition.getTetromino().getFourNeighbors(tetrominoPosition.getSpin())) {
            int x = tetrominoPosition.getX() + point.x;
            int y = tetrominoPosition.getY() + point.y;

            if (field.getCellState(x, y) == CellState.WALL
                    || field.getCellState(x, y) == state) {
                count ++;

                if (count > threshold) {
                    return false;
                }

            }
        }

        return true;

    }
}
