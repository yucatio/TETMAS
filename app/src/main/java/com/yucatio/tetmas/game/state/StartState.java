package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum StartState implements GameState {
    INSTANCE;
    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        context.initialize();

        context.changeState(StartAnimationState.INSTANCE);
    }

    @Override
    public String toString() {
        return "StartState:" + super.toString();
    }

}
