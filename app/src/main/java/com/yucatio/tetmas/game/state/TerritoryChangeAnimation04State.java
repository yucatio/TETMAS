package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.view.GameWorldLayout;

public enum TerritoryChangeAnimation04State implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 0.6f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {
        // nothing to do
    }

    @Override
    public void execute(GameWorld context, float deltaTime) {
        context.updateDisplayScore(context.getAnotherPlayerIndex(), deltaTime/GameWorldLayout.changeCellDelay);

        float stateRemainTime = GameWorldLayout.changeCellDelay * context.getNewlySurroundedCellAnother().size() + STATE_REMAIN_TIME;
        if (context.getStateTime() >= stateRemainTime) {
            context.clearNewlySurroundedCellAnother();
            if (context.getNewlyDeadCell2().size() > 0) {
                context.changeState(TerritoryChangeAnimation05State.INSTANCE);
            } else {
                context.changeState(ChangePlayerState.INSTANCE);
            }
        }
    }

    @Override
    public String toString() {
        return "TerritoryChangeAnimation04State:" + super.toString();
    }

}
