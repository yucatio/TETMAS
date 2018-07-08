package com.yucatio.tetmas.game.player;

import com.yucatio.tetmas.game.element.GameField;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;

import java.io.Serializable;
import java.util.EnumMap;

public abstract class Player implements Serializable {

    public abstract void setHand(GameField field, int playerIndex, int playerIndexAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother);
    public abstract TetrominoPosition getHand();
    public abstract boolean isAI();

}
