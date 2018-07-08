package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum TerritoryChangeAnimation01State implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 0.1f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        context.updateDisplayScore(deltaTime / STATE_REMAIN_TIME);

        float stateRemainTime = STATE_REMAIN_TIME * 4 + 0.01f;
        if (context.getStateTime() >= stateRemainTime) {
            if (context.getNewlySurroundedCell().size() > 0) {
                context.changeState(TerritoryChangeAnimation02State.INSTANCE);
                return;
            } else if (context.getNewlyDeadCell().size() > 0) {
                context.changeState(TerritoryChangeAnimation03State.INSTANCE);
                return;
            } else if (context.getNewlySurroundedCellAnother().size() > 0) {
                context.changeState(TerritoryChangeAnimation04State.INSTANCE);
                return;
            } else if (context.getNewlyDeadCell2().size() > 0) {
                context.changeState(TerritoryChangeAnimation05State.INSTANCE);
                return;
            } else {
                context.changeState(ChangePlayerState.INSTANCE);
            }

        }

    }

    @Override
    public String toString() {
        return "TerritoryChangeAnimation01State:" + super.toString();
    }

}
