package com.yucatio.tetmas.game.strategy;

import com.yucatio.tetmas.game.element.CellState;
import com.yucatio.tetmas.game.element.GameField;
import com.yucatio.tetmas.game.element.Point;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class SimplePutStrategy implements Strategy,Serializable {

    @Override
    public TetrominoPosition getTetrominoPosition(GameField field, CellState state, CellState stateAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother) {
        // 置けるところに置きます
        TetrominoPosition hand;
        // foreach 空いているセル
        for (Set<Point> pointSet : field.getEmptyCellClusterSet()) {
            for (Point point : pointSet) {
                // foreach テトロミノ
                for (Map.Entry<Tetromino, Integer> e : availableTetromino.entrySet()) {
                    Tetromino tetromino = e.getKey();
                    if (e.getValue() <= 0) {
                        continue;
                    }
                    // foreach spin
                    for (int spin = 0; spin < tetromino.getSpinNum(); spin++) {
                        Point[] tetrominoPoints = tetromino.getTetrominoPoints(spin);
                        int offsetX = tetrominoPoints[0].x;
                        int offsetY = tetrominoPoints[0].y;
                        TetrominoPosition tetrominoPosition = new TetrominoPosition(tetromino, point.x - offsetX, point.y - offsetY, spin);
                        if (field.canPlaceTetromino(tetrominoPosition)) {
                            return tetrominoPosition;
                        }

                    }

                }
            }
        }

        return null;
    }
}
