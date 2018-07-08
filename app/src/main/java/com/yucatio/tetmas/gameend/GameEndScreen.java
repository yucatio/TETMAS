package com.yucatio.tetmas.gameend;

import android.content.Context;

import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.view.GameWorldRenderer;
import com.yucatio.tetmas.gameend.view.GameEndLayout;
import com.yucatio.tetmas.gameend.view.GameEndRenderer;
import com.yucatio.tetmas.mainmenu.MainMenuScreen;
import com.yucatio.tetmas.selectturn.SelectTurnScreen;
import com.yucatio.tetmas.util.OverlapTester;

import java.util.List;

public class GameEndScreen extends Screen{
    private Context context;
    private GameEndScreenManager manager;
    private GameWorldRenderer gameWorldRenderer;
    private GameEndRenderer renderer;
    private GameWorld gameWorld;

    public GameEndScreen(Game game, Context context, GameWorld gameWorld) {
        super(game);

        this.context = context;
        this.gameWorld = gameWorld;

        manager = new GameEndScreenManager(context, gameWorld);
        gameWorldRenderer = new GameWorldRenderer(gameWorld);
        renderer = new GameEndRenderer(manager);

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
                game.setScreen(new SelectTurnScreen(game, context, gameWorld.getStage(), gameWorld.getFieldSize()));
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
