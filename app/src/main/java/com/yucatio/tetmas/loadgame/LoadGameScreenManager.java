package com.yucatio.tetmas.loadgame;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.loadgame.view.LoadGameLayout;
import com.yucatio.tetmas.util.OverlapTester;

public class LoadGameScreenManager {
    private Stage stage;
    private FieldSize fieldSize;

    private Button resumeGameButton = new Button();
    private Button newGameButton = new Button();
    private Button backButton = new Button();

    private Button[] buttons = {resumeGameButton, newGameButton, backButton};

    public LoadGameScreenManager(Stage stage, FieldSize fieldSize) {
        this.stage = stage;
        this.fieldSize = fieldSize;
    }

    public void update(Input.TouchEvent touchEvent) {
        for (Button button : buttons) {
            button.setState(Button.ENABLE);
        }

        if (touchEvent.type != Input.TouchEvent.TOUCH_DOWN && touchEvent.type != Input.TouchEvent.TOUCH_DRAGGED) {
            return;
        }

        if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, LoadGameLayout.resumeGameButton)) {
            resumeGameButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, LoadGameLayout.newGameButton)) {
            newGameButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, LoadGameLayout.backButton)) {
            backButton.setState(Button.SELECTED);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public FieldSize getFieldSize() {
        return fieldSize;
    }

    public Button getResumeGameButton() {
        return resumeGameButton;
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}
