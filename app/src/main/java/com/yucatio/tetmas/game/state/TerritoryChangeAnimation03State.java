package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public enum TerritoryChangeAnimation03State implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 0.9f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        if (context.getStateTime() >= STATE_REMAIN_TIME) {
            context.clearNewlyDeadCell();
            if (context.getNewlySurroundedCellAnother().size() > 0) {
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
        return "TerritoryChangeAnimation03State:" + super.toString();
    }

}
