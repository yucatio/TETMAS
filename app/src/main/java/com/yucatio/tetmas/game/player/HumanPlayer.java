package com.yucatio.tetmas.game.player;

import com.yucatio.tetmas.game.element.GameField;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;

import java.io.Serializable;
import java.util.EnumMap;

public class HumanPlayer extends Player implements Serializable {

    @Override
    public void setHand(GameField field, int playerIndex, int playerIndexAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother) {

    }

    @Override
    public TetrominoPosition getHand() {
        return null;
    }

    @Override
    public boolean isAI() {
        return false;
    }
}
