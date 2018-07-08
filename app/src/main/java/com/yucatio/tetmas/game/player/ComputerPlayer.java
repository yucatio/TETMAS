package com.yucatio.tetmas.game.player;

import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.element.GameField;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;
import com.yucatio.tetmas.game.strategy.EasyStrategy001;
import com.yucatio.tetmas.game.strategy.HardStageStrategy001;
import com.yucatio.tetmas.game.strategy.MiddleStrategy001;
import com.yucatio.tetmas.game.strategy.Strategy;

import java.io.Serializable;
import java.util.EnumMap;

public class ComputerPlayer extends Player implements Serializable {
    private static EnumMap<Stage, EnumMap<FieldSize, Strategy>> strategyMap = new EnumMap<Stage, EnumMap<FieldSize, Strategy>>(Stage.class) {{
        put(Stage.EASY, new EnumMap<FieldSize, Strategy>(FieldSize.class) {{
          put(FieldSize.FIFTEEN_SQUARE, new EasyStrategy001(FieldSize.FIFTEEN_SQUARE));
          put(FieldSize.TWENTY_SQUARE, new EasyStrategy001(FieldSize.TWENTY_SQUARE));
        }});

        put(Stage.MIDDLE, new EnumMap<FieldSize, Strategy>(FieldSize.class) {{
            put(FieldSize.FIFTEEN_SQUARE, new MiddleStrategy001(FieldSize.FIFTEEN_SQUARE));
            put(FieldSize.TWENTY_SQUARE, new MiddleStrategy001(FieldSize.TWENTY_SQUARE));
        }});

        put(Stage.HARD, new EnumMap<FieldSize, Strategy>(FieldSize.class) {{
            put(FieldSize.FIFTEEN_SQUARE, new HardStageStrategy001(FieldSize.FIFTEEN_SQUARE));
            put(FieldSize.TWENTY_SQUARE, new HardStageStrategy001(FieldSize.TWENTY_SQUARE));
        }});
    }};

    private Strategy strategy;
    private TetrominoPosition hand;

    public ComputerPlayer(Stage stage, FieldSize fieldSize) {
        strategy = strategyMap.get(stage).get(fieldSize);
    }

    @Override
    public void setHand(GameField field, int playerIndex, int playerIndexAnother, EnumMap<Tetromino, Integer> availableTetromino, EnumMap<Tetromino, Integer> availableTetrominoAnother) {
        hand = strategy.getTetrominoPosition(field, field.getPlayerCellState(playerIndex), field.getPlayerCellState(playerIndexAnother), availableTetromino, availableTetrominoAnother);
    }

    @Override
    public TetrominoPosition getHand() {
        return hand;
    }

    @Override
    public boolean isAI() {
        return true;
    }
}
