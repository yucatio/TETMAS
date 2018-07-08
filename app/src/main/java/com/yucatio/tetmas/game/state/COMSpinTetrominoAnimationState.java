package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.element.TetrominoPosition;

public enum COMSpinTetrominoAnimationState implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 0.2f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }


    @Override
    public void execute(GameWorld context, float deltaTime) {
        if (context.getStateTime() >= STATE_REMAIN_TIME) {

            TetrominoPosition dist = context.getHand();
            TetrominoPosition current = context.getActiveTetrominoPosition();

            if (dist.getSpin() == current.getSpin()) {
                context.changeState(COMMoveTetrominoAnimationState.INSTANCE);
                return;
            }

            context.spinTetromino();

            context.changeState(COMSpinTetrominoAnimationState.INSTANCE);

        }
    }

    @Override
    public String toString() {
        return "COMSpinTetrominoAnimationState:" + super.toString();
    }


}
