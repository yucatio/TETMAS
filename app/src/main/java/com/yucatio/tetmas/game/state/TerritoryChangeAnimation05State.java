package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum TerritoryChangeAnimation05State implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 0.9f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        if (context.getStateTime() >= STATE_REMAIN_TIME) {
            context.clearNewlyDeadCell2();
            context.changeState(ChangePlayerState.INSTANCE);
        }
    }

    @Override
    public String toString() {
        return "TerritoryChangeAnimation05State:" + super.toString();
    }

}
