package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum CheckSpaceState implements GameState {
    INSTANCE;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {
    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        if (context.checkTetrominoSpace()) {
            context.changeState(TurnAnimationState.INSTANCE);
        } else {
            context.changeState(CheckSpaceAnotherState.INSTANCE);
        }
    }

    @Override
    public String toString() {
        return "CheckSpaceState:" + super.toString();
    }

}