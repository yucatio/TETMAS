package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum TurnAnimationState implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 0.1f;
    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        if (context.getStateTime() >= STATE_REMAIN_TIME) {
            if (context.getCurrentPlayer().isAI()) {
                // COM player
                context.changeState(COMGetHandState.INSTANCE);
            } else {
                // 人間プレイヤー
                context.changeState(SelectTetrominoState.INSTANCE);
            }

        }
    }

    @Override
    public String toString() {
        return "TurnAnimationState:" + super.toString();
    }

}
