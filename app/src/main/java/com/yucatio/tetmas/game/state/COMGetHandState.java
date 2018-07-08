package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum COMGetHandState implements GameState {
    INSTANCE;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        context.setHand();
        context.changeState(COMSelectTetrominoAnimationState.INSTANCE);

    }

    @Override
    public String toString() {
        return "GetHandState:" + super.toString();
    }

}
