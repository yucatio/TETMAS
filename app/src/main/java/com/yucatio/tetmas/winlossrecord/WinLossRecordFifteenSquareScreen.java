package com.yucatio.tetmas.winlossrecord;

import android.content.Context;

import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.mainmenu.MainMenuScreen;
import com.yucatio.tetmas.util.OverlapTester;
import com.yucatio.tetmas.winlossrecord.view.WinLossRecordLayout;
import com.yucatio.tetmas.winlossrecord.view.WinLossRecordRenderer;

import java.util.List;

public class WinLossRecordFifteenSquareScreen extends Screen {
    private Context context;
    private WinLossRecordScreenManager manager;
    private WinLossRecordRenderer renderer;

    public WinLossRecordFifteenSquareScreen(Game game, Context context) {
        super(game);
        this.context = context;

        manager = new WinLossRecordScreenManager(context, FieldSize.FIFTEEN_SQUARE, true, false);
        renderer = new WinLossRecordRenderer(manager);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (Input.TouchEvent touchEvent : touchEvents) {
            manager.update(touchEvent);

            if (touchEvent.type != Input.TouchEvent.TOUCH_UP) {
                continue;
            }

            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, WinLossRecordLayout.nextButton)) {
                game.setScreen(new WinLossRecordTwentySquareScreen(game, context));
                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, WinLossRecordLayout.backButton)) {
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
