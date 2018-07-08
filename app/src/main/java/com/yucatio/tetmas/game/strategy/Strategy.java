package com.yucatio.tetmas.game.strategy;

import com.yucatio.tetmas.game.element.CellState;
import com.yucatio.tetmas.game.element.GameField;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;

import java.util.EnumMap;

public interface Strategy {
    public TetrominoPosition getTetrominoPosition(GameField field, CellState state, CellState stateAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother);
}
