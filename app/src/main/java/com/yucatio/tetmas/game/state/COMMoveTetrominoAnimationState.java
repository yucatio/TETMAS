package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.element.Direction;
import com.yucatio.tetmas.game.element.TetrominoPosition;

public enum COMMoveTetrominoAnimationState implements GameState {
    INSTANCE;

    private static final float STATE_REMAIN_TIME = 0.1f;

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {

    }


    @Override
    public void execute(GameWorld context, float deltaTime) {
        if (context.getStateTime() >= STATE_REMAIN_TIME) {

            TetrominoPosition dist = context.getHand();
            TetrominoPosition current = context.getActiveTetrominoPosition();

            if (dist.getX() == current.getX() && dist.getY() == current.getY()) {
                context.placeTetromino();
                context.changeState(TerritoryChangeAnimation01State.INSTANCE);
                return;
            }

            if (dist.getX() > current.getX()) {
                context.moveTetromino(Direction.RIGHT);
            } else if (dist.getX() < current.getX()) {
                context.moveTetromino(Direction.LEFT);
            } else if (dist.getY() > current.getY()) {
                context.moveTetromino(Direction.BOTTOM);
            } else if (dist.getY() < current.getY()) {
                context.moveTetromino(Direction.TOP);
            }

            context.changeState(COMMoveTetrominoAnimationState.INSTANCE);

        }
    }

    @Override
    public String toString() {
        return "COMMoveTetrominoAnimationState:" + super.toString();
    }


}
