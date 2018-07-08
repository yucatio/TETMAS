package com.yucatio.tetmas.game;

import android.content.Context;
import android.util.Log;

import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.state.GameEndState;
import com.yucatio.tetmas.game.view.GameWorldRenderer;
import com.yucatio.tetmas.gameend.GameEndScreen;
import com.yucatio.tetmas.gameend.GameEndTwoPlayerScreen;
import com.yucatio.tetmas.io.GameDataFile;
import com.yucatio.tetmas.io.WinLossRecordFile;
import com.yucatio.tetmas.mainmenu.MainMenuScreen;
import com.yucatio.tetmas.view.TetmasRenderer;

import java.io.IOException;
import java.util.List;

public class GameScreen extends Screen {
    private static final String TAG = "GameScreen";
    private Context context;
    private GameWorld gameWorld;
    private TetmasRenderer renderer;

    public GameScreen(Game game, Context context, Stage stage, FieldSize fieldSize, boolean isGoFirst) {
        super(game);
        this.context = context;
        gameWorld = new GameWorld(stage, fieldSize, isGoFirst);
        renderer = new GameWorldRenderer(gameWorld);
    }

    public GameScreen(Game game, Context context, GameWorld gameWorld) {
        super(game);
        this.context = context;
        this.gameWorld = gameWorld;
        renderer = new GameWorldRenderer(gameWorld);
    }

    @Override
    public void update(float deltaTime) {
        if (deltaTime > 1.0f) {
            deltaTime = 1.0f;
        }



        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (Input.TouchEvent event : touchEvents) {
            gameWorld.update(event);
        }

        gameWorld.update(deltaTime);

        if (gameWorld.getState() == GameEndState.INSTANCE) {
            if (gameWorld.getStage() == Stage.TWO_PLAYER) {
              game.setScreen(new GameEndTwoPlayerScreen(game, context, gameWorld));
              return;
            }

            try {
                // 勝敗を記録

                WinLossRecordFile dataFile = new WinLossRecordFile(context, gameWorld.getStage(), gameWorld.getFieldSize());

                // COM対戦
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
                    dataFile.recordWin();
                } else if (playerScore == comScore) {
                    dataFile.recordDraw();
                } else {
                    dataFile.recordLose();
                }

                game.setScreen(new GameEndScreen(game, context, gameWorld));

            } catch (IOException e) {
                Log.e(TAG, "IOException occurred when save score.stage=" + gameWorld.getStage(), e);
                game.setScreen(new MainMenuScreen(game, context));

            }

        }

    }

    @Override
    public void present(float deltaTime) {
        renderer.render();
    }

    @Override
    public void pause() {
        if (gameWorld.getState() == GameEndState.INSTANCE) {
            deleteGameData();
        } else {
            saveGameData();
        }
    }

    private void saveGameData() {
        try {
            GameDataFile dataFile = new GameDataFile(context, gameWorld.getStage(), gameWorld.getFieldSize());
            dataFile.save(gameWorld);
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred when save data.stage=" + gameWorld.getStage(), e);
        }
    }

    private void deleteGameData() {
        try {
            GameDataFile dataFile = new GameDataFile(context, gameWorld.getStage(), gameWorld.getFieldSize());
            dataFile.delete();
        } catch (Exception e) {
            Log.e(TAG, "Exception occurred when remove data.stage=" + gameWorld.getStage(), e);
        }
    }

    @Override
    public void resume() {
        // nothing to do
    }

    @Override
    public void dispose() {
        // nothing to do
    }

    @Override
    public Screen getPreviousScreen() {
        return new MainMenuScreen(game, context);
    }
}
