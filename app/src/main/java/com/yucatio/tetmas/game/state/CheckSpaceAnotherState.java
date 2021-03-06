package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum CheckSpaceAnotherState implements GameState {
    INSTANCE;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        int player = context.getAnotherPlayerIndex();

        if (context.checkTetrominoSpace(player)) {
            context.changeState(PassAnimationState.INSTANCE);
        } else {
            context.changeState(GameEndAnimationState.INSTANCE);
        }

    }

    @Override
    public String toString() {
        return "CheckSpaceAnotherState:" + super.toString();
    }

}