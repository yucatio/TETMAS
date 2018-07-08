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

public class RandomPutStrategy implements Strategy, Serializable {
    private List<Tetromino> tetrominoList = Arrays.asList(Tetromino.values());
    private Random random = new Random();

    @Override
    public TetrominoPosition getTetrominoPosition(GameField field, CellState state, CellState stateAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother) {
        int nextX = random.nextInt(field.getGameWidth());
        int nextY = random.nextInt(field.getGameHeight());

        Collections.shuffle(tetrominoList);

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
                            return tetrominoPosition;
                        }

                    }
                }
            }
        }
        return null;
    }

}
