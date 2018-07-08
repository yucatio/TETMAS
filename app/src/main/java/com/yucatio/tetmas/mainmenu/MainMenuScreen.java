package com.yucatio.tetmas.mainmenu;

import android.content.Context;

import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.help.HelpScreen;
import com.yucatio.tetmas.mainmenu.view.MainMenuLayout;
import com.yucatio.tetmas.mainmenu.view.MainMenuRenderer;
import com.yucatio.tetmas.selectfieldsize.SelectFieldSizeScreen;
import com.yucatio.tetmas.util.OverlapTester;
import com.yucatio.tetmas.winlossrecord.WinLossRecordFifteenSquareScreen;

import java.util.List;

public class MainMenuScreen extends Screen {
    private Context context;

    private MainMenuScreenManager manager;
    private MainMenuRenderer renderer;

    public MainMenuScreen(Game game, Context context) {
        super(game);
        this.context = context;

        manager = new MainMenuScreenManager();
        renderer = new MainMenuRenderer(manager);

    }

    @Override
    public void update(float deltaTime) {
//        if (deltaTime > 1.0f) {
//            deltaTime = 1.0f;
//        }

        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (Input.TouchEvent touchEvent : touchEvents) {
            manager.update(touchEvent);

            if (touchEvent.type != Input.TouchEvent.TOUCH_UP) {
                continue;
            }

            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.easyLevel)) {
                Stage stage = Stage.EASY;
                game.setScreen(new SelectFieldSizeScreen(game, context, stage));
                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.middleLevel)) {
                Stage stage = Stage.MIDDLE;
                game.setScreen(new SelectFieldSizeScreen(game, context, stage));
                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.hardLevel)) {
                Stage stage = Stage.HARD;
                game.setScreen(new SelectFieldSizeScreen(game, context, stage));
                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.twoPlayer)) {
                Stage stage = Stage.TWO_PLAYER;
                game.setScreen(new SelectFieldSizeScreen(game, context, stage));
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.winLossRecords)) {
                game.setScreen(new WinLossRecordFifteenSquareScreen(game, context));
                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.help)) {
                game.setScreen(new HelpScreen(game, context));
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
        return null;
    }
}
