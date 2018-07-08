package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.view.GameWorldLayout;
import com.yucatio.tetmas.util.OverlapTester;

import java.util.EnumMap;
import java.util.Map;

public enum SelectTetrominoState implements GameState {
    INSTANCE;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {
        // ボタンをリセット
        context.resetTouchedButton();

        int playerIndex = context.getPlayerIndex();
        EnumMap<Tetromino, float[]> tetrominoLayout = GameWorldLayout.tetrominoButtonMapList.get(playerIndex);

        // テトロミノが選択されたかチェック
        Tetromino selectedTetromino = null;
        for (Map.Entry<Tetromino, float[]> e : tetrominoLayout.entrySet()) {
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, e.getValue())
                    && context.isTetominoEnable(playerIndex, e.getKey())) {
                selectedTetromino = e.getKey();
                break;
            }
        }

        if (selectedTetromino == null) {
            return;
        }

        if (touchEvent.type == Input.TouchEvent.TOUCH_UP) {
            context.tetrominoSelected(selectedTetromino);
            context.enableTetrominoControlButton();
            context.changeState(MoveTetrominoState.INSTANCE);
        } else if (touchEvent.type == Input.TouchEvent.TOUCH_DOWN || touchEvent.type == Input.TouchEvent.TOUCH_DRAGGED) {
            context.tetrominoButtonTouched(selectedTetromino);

        }
    }

    @Override
    public void execute(GameWorld context, float deltaTime) {

    }

    @Override
    public String toString() {
        return "SelectTetrominoState:" + super.toString();
    }

}