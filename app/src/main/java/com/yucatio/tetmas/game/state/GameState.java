package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;

public interface GameState {
    public void execute(GameWorld context, Input.TouchEvent touchEvent);
    public void execute(GameWorld context, float deltaTime);
}
