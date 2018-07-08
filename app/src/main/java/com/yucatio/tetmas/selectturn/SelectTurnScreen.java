package com.yucatio.tetmas.selectturn;

import android.content.Context;

import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.game.GameScreen;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.selectfieldsize.SelectFieldSizeScreen;
import com.yucatio.tetmas.selectturn.view.SelectTurnLayout;
import com.yucatio.tetmas.selectturn.view.SelectTurnRenderer;
import com.yucatio.tetmas.util.OverlapTester;

import java.util.List;

public class SelectTurnScreen extends Screen {
    private Context context;
    private Stage stage;
    private FieldSize fieldSize;
    private SelectTurnScreenManager manager;
    private SelectTurnRenderer renderer;

    public SelectTurnScreen(Game game, Context context, Stage stage, FieldSize fieldSize) {
        super(game);
        this.context = context;
        this.stage = stage;
        this.fieldSize = fieldSize;
        manager = new SelectTurnScreenManager(stage, fieldSize);
        renderer = new SelectTurnRenderer(manager);
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

            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectTurnLayout.firstMoverButton)) {
                game.setScreen(new GameScreen(game, context, stage, fieldSize, true));
                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectTurnLayout.secondMoverButton)) {
                game.setScreen(new GameScreen(game, context, stage, fieldSize, false));
                return;
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectTurnLayout.backButton)) {
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public Screen getPreviousScreen() {
        return new SelectFieldSizeScreen(game, context, stage);
    }
}
