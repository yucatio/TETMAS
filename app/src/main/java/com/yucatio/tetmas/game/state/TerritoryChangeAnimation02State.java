package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.view.GameWorldLayout;

public enum TerritoryChangeAnimation02State implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 0.6f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        context.updateDisplayScore(deltaTime / GameWorldLayout.changeCellDelay);
        float stateRemainTime = GameWorldLayout.changeCellDelay * context.getNewlySurroundedCell().size() + STATE_REMAIN_TIME;
        if (context.getStateTime() >= stateRemainTime) {
            context.clearNewlySurroundedCell();

            if (context.getNewlyDeadCell().size() > 0) {
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
        return "TerritoryChangeAnimation02State:" + super.toString();
    }

}
