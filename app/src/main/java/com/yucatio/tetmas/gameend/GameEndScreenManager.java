package com.yucatio.tetmas.gameend;

import android.content.Context;
import android.util.Log;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.attribute.Outcome;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.attribute.WinLossRecord;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.gameend.view.GameEndLayout;
import com.yucatio.tetmas.io.WinLossRecordFile;
import com.yucatio.tetmas.util.OverlapTester;

import java.io.IOException;

public class GameEndScreenManager {
    private static final String TAG = "GameEndScreenManager";

    private Button newGameButton = new Button();
    private Button backButton = new Button();

    private Button[] buttons = {newGameButton, backButton};

    private Stage stage;
    private Outcome outcome;
    private WinLossRecord winLossRecord;

    public GameEndScreenManager(Context context, GameWorld gameWorld) {
        stage = gameWorld.getStage();

        int playerScore;
        int comScore;

        if (gameWorld.isGoFirst()) {
            // 先攻
            playerScore = gameWorld.getScore(GameWorld.PLAYER_PINK);
            comScore = gameWorld.getScore(GameWorld.PLAYER_BLUE);
        } else {
            playerScore = gameWorld.getScore(GameWorld.PLAYER_BLUE);
            comScore = gameWorld.getScore(GameWorld.PLAYER_PINK);
        }

        if (playerScore > comScore) {
            outcome = Outcome.WIN;
        } else if (playerScore == comScore) {
            outcome = Outcome.DRAW;
        } else {
            outcome = Outcome.LOSE;
        }

        WinLossRecordFile dataFile = new WinLossRecordFile(context, gameWorld.getStage(), gameWorld.getFieldSize());
        try {
            winLossRecord = dataFile.getWinLossRecord();
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred. stage=" + gameWorld.getStage().getGameDataId(), e);
            winLossRecord = new WinLossRecord();
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

    public Stage getStage() {
        return stage;
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public WinLossRecord getWinLossRecord() {
        return winLossRecord;
    }
}
