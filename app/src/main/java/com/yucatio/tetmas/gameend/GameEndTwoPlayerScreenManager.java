package com.yucatio.tetmas.gameend;

import android.content.Context;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.gameend.view.GameEndLayout;
import com.yucatio.tetmas.util.OverlapTester;

public class GameEndTwoPlayerScreenManager {
    private static final String TAG = "GameEndTowPlayerScrMng";

    private Button newGameButton = new Button();
    private Button backButton = new Button();

    private Button[] buttons = {newGameButton, backButton};

    private int winPlayer;

    public GameEndTwoPlayerScreenManager(Context context, GameWorld gameWorld) {
        int playerPinkScore = gameWorld.getScore(GameWorld.PLAYER_PINK);
        int playerBlueScore = gameWorld.getScore(GameWorld.PLAYER_BLUE);

        if (playerPinkScore > playerBlueScore) {
            winPlayer = GameWorld.PLAYER_PINK;
        } else if(playerBlueScore > playerPinkScore) {
            winPlayer = GameWorld.PLAYER_BLUE;
        } else {
            winPlayer = 2;
        }
    }

    public void update(Input.TouchEvent touchEvent) {
        for (Button button : buttons) {
            button.setState(Button.ENABLE);
        }

        if (touchEvent.type != Input.TouchEvent.TOUCH_DOWN && touchEvent.type != Input.TouchEvent.TOUCH_DRAGGED) {
            return;
        }

        if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameEndLayout.newGameButton)) {
            newGameButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameEndLayout.backButton)) {
            backButton.setState(Button.SELECTED);
        }
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public int getWinPlayer() {
        return winPlayer;
    }
}
