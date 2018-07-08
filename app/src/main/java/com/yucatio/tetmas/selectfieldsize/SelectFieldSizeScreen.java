package com.yucatio.tetmas.selectfieldsize;

import android.content.Context;

import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.game.GameScreen;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.io.GameDataFile;
import com.yucatio.tetmas.loadgame.LoadGameScreen;
import com.yucatio.tetmas.mainmenu.MainMenuScreen;
import com.yucatio.tetmas.selectfieldsize.view.SelectFieldSizeLayout;
import com.yucatio.tetmas.selectfieldsize.view.SelectFieldSizeRenderer;
import com.yucatio.tetmas.selectturn.SelectTurnScreen;
import com.yucatio.tetmas.util.OverlapTester;

import java.util.List;

public class SelectFieldSizeScreen extends Screen {
    private Context context;
    private Stage stage;
    private SelectFieldSizeScreenManager manager;
    private SelectFieldSizeRenderer renderer;

    public SelectFieldSizeScreen(Game game, Context context, Stage stage) {
        super(game);

        this.context = context;
        this.stage = stage;

        manager = new SelectFieldSizeScreenManager(stage);
        renderer = new SelectFieldSizeRenderer(manager);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for (Input.TouchEvent touchEvent : touchEvents) {
            manager.update(touchEvent);

            if (touchEvent.type != Input.TouchEvent.TOUCH_UP) {
                continue;
            }

            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectFieldSizeLayout.field15Button)) {
                // 15 x 15

                FieldSize fieldSize = FieldSize.FIFTEEN_SQUARE;
                // セーブデータがあるかどうか
                GameDataFile file = new GameDataFile(context, stage, fieldSize);
                if (file.exists()) {
                    // セーブデータが存在する場合
                    game.setScreen(new LoadGameScreen(game, context, stage, fieldSize));
                    return;
                } else {
                    // セーブデータが存在しない場合
                    if (stage == Stage.TWO_PLAYER) {
                        game.setScreen(new GameScreen(game, context, stage, fieldSize, true));
                        return;
                    } else {
                        game.setScreen(new SelectTurnScreen(game, context, stage, fieldSize));
                        return;
                    }
                }

            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectFieldSizeLayout.field20Button)) {
                // 20 x 20
                FieldSize fieldSize = FieldSize.TWENTY_SQUARE;
                // セーブデータがあるかどうか
                GameDataFile file = new GameDataFile(context, stage, fieldSize);
                if (file.exists()) {
                    // セーブデータが存在する場合
                    game.setScreen(new LoadGameScreen(game, context, stage, fieldSize));
                    return;
                } else {
                    // セーブデータが存在しない場合
                    if (stage == Stage.TWO_PLAYER) {
                        game.setScreen(new GameScreen(game, context, stage, fieldSize, true));
                        return;
                    } else {
                        game.setScreen(new SelectTurnScreen(game, context, stage, fieldSize));
                        return;
                    }
                }

            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectFieldSizeLayout.backButton)) {
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
