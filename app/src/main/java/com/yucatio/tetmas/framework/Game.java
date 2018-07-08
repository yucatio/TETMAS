package com.yucatio.tetmas.framework;

public interface Game {
    public Input getInput();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();

}
