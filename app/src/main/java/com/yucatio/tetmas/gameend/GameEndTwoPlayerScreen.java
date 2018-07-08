package com.yucatio.tetmas.gameend;

import android.content.Context;

import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.game.GameScreen;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.view.GameWorldRenderer;
import com.yucatio.tetmas.gameend.view.GameEndLayout;
import com.yucatio.tetmas.gameend.view.GameEndTwoPlayerRenderer;
import com.yucatio.tetmas.mainmenu.MainMenuScreen;
import com.yucatio.tetmas.util.OverlapTester;

import java.util.List;

public class GameEndTwoPlayerScreen extends Screen {
    private Context context;
    private GameWorld gameWorld;

    private GameEndTwoPlayerScreenManager manager;
    private GameWorldRenderer gameWorldRenderer;
    private GameEndTwoPlayerRenderer renderer;

    public GameEndTwoPlayerScreen(Game game, Context context, GameWorld gameWorld) {
        super(game);

        this.context = context;
        this.gameWorld = gameWorld;

        manager = new GameEndTwoPlayerScreenManager(context, gameWorld);
        gameWorldRenderer = new GameWorldRenderer(gameWorld);
        renderer = new GameEndTwoPlayerRenderer(manager);

    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (Input.TouchEvent touchEvent : touchEvents) {
            manager.update(touchEvent);

            if (touchEvent.type != Input.TouchEvent.TOUCH_UP) {
                continue;
            }

            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameEndLayout.newGameButton)) {
                game.setScreen(new GameScreen(game, context, Stage.TWO_PLAYER, gameWorld.getFieldSize(), true));
                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameEndLayout.backButton)) {
                game.setScreen(getPreviousScreen());
                return;

            }
        }

    }


    @Override
    public void present(float deltaTime) {
        gameWorldRenderer.render();
        renderer.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public Screen getPreviousScreen() {
        return new MainMenuScreen(game, context);
    }
}
