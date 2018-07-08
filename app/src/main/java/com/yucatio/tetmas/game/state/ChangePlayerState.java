package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum ChangePlayerState implements GameState {
    INSTANCE;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {
    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        context.changePlayer();
        context.changeState(CheckSpaceState.INSTANCE);
    }

    @Override
    public String toString() {
        return "ChangePlayerState:" + super.toString();
    }

}
