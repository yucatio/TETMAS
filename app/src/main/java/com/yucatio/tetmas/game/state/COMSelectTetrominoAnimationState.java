package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.element.Tetromino;

public enum COMSelectTetrominoAnimationState implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 0.8f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        Tetromino tetromino = context.getHand().getTetromino();
        context.tetrominoButtonTouched(tetromino);

        if (context.getStateTime() >= STATE_REMAIN_TIME) {
            context.resetTouchedButton();
            context.tetrominoSelected(tetromino);
            context.changeState(COMSpinTetrominoAnimationState.INSTANCE);
        }
    }

    @Override
    public String toString() {
        return "COMSelectTetrominoAnimationState:" + super.toString();
    }

}
