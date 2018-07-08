package com.yucatio.tetmas.loadgame;

import android.content.Context;
import android.util.Log;

import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.game.GameScreen;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.io.GameDataFile;
import com.yucatio.tetmas.loadgame.view.LoadGameLayout;
import com.yucatio.tetmas.loadgame.view.LoadGameRenderer;
import com.yucatio.tetmas.mainmenu.MainMenuScreen;
import com.yucatio.tetmas.selectfieldsize.SelectFieldSizeScreen;
import com.yucatio.tetmas.selectturn.SelectTurnScreen;
import com.yucatio.tetmas.util.OverlapTester;

import java.util.List;

public class LoadGameScreen extends Screen {
    private static final String TAG = "LoadGameScreen";

    private Context context;
    private Stage stage;
    private FieldSize fieldSize;
    private LoadGameScreenManager manager;
    private LoadGameRenderer renderer;


    public LoadGameScreen(Game game, Context context, Stage stage, FieldSize fieldSize) {
        super(game);

        this.context = context;
        this.stage = stage;
        this.fieldSize = fieldSize;

        manager = new LoadGameScreenManager(stage, fieldSize);
        renderer = new LoadGameRenderer(manager);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (Input.TouchEvent touchEvent : touchEvents) {
            manager.update(touchEvent);

            if (touchEvent.type != Input.TouchEvent.TOUCH_UP) {
                continue;
            }

            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, LoadGameLayout.resumeGameButton)) {
                // resume game
                try {
                    GameDataFile dataFile = new GameDataFile(context, stage, fieldSize);

                    GameWorld gameWorld = dataFile.load();
                    game.setScreen(new GameScreen(game, context, gameWorld));
                } catch (Exception e) {
                    Log.e(TAG, "Exception occurred when load data.", e);
                    game.setScreen(new MainMenuScreen(game, context));
                }

                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, LoadGameLayout.newGameButton)) {
                if (stage == Stage.TWO_PLAYER) {
                    game.setScreen(new GameScreen(game, context, stage, fieldSize, true));
                } else {
                    game.setScreen(new SelectTurnScreen(game, context, stage, fieldSize));
                }
                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, LoadGameLayout.backButton)) {
                game.setScreen(getPreviousScreen());
                return;
            }
        }

    }

    @Override
    public void present(float deltaTime) {
        renderer.render();
    }

    @Override
    public void pause() {
        // nothing to do
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
        return new SelectFieldSizeScreen(game, context, stage);
    }
}
