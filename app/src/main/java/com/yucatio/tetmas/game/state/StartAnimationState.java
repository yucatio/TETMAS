package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum StartAnimationState implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 2.0f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        if (context.getStateTime() >= STATE_REMAIN_TIME) {
            context.changeState(CheckSpaceState.INSTANCE);
        }
    }

    @Override
    public String toString() {
        return "StartAnimationState:" + super.toString();
    }

}