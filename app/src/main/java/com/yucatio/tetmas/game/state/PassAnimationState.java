package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum PassAnimationState implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 2.0f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {
    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        if (context.getStateTime() >= STATE_REMAIN_TIME) {
            context.changeState(ChangePlayerState.INSTANCE);
        }
    }

    @Override
    public String toString() {
        return "PassAnimationState:" + super.toString();
    }

}