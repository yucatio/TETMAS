package com.yucatio.tetmas.help;

import android.content.Context;

import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.help.view.HelpLayout;
import com.yucatio.tetmas.help.view.HelpRenderer;
import com.yucatio.tetmas.mainmenu.MainMenuScreen;
import com.yucatio.tetmas.util.OverlapTester;

import java.util.List;

public class HelpScreen extends Screen {
    private Context context;
    private HelpScreenManager manager;
    private HelpRenderer renderer;

    public HelpScreen(Game game, Context context) {
        super(game);
        this.context = context;
        manager = new HelpScreenManager();
        renderer = new HelpRenderer(manager);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (Input.TouchEvent touchEvent : touchEvents) {
            manager.update(touchEvent);

            if (touchEvent.type != Input.TouchEvent.TOUCH_UP) {
                continue;
            }

            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, HelpLayout.backButton)) {
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
        return new MainMenuScreen(game, context);
    }
}
