package com.yucatio.tetmas.selectturn;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.selectturn.view.SelectTurnLayout;
import com.yucatio.tetmas.util.OverlapTester;

public class SelectTurnScreenManager {
    private Stage stage;
    private FieldSize fieldSize;

    private Button firstMoverButton = new Button();
    private Button secondMoverButton = new Button();
    private Button backButton = new Button();
    private Button[] buttons = {firstMoverButton, secondMoverButton, backButton};

    public SelectTurnScreenManager(Stage stage, FieldSize fieldSize) {
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

        if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectTurnLayout.firstMoverButton)) {
            firstMoverButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectTurnLayout.secondMoverButton)) {
            secondMoverButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectTurnLayout.backButton)) {
            backButton.setState(Button.SELECTED);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public FieldSize getFieldSize() {
        return fieldSize;
    }

    public Button getFirstMoverButton() {
        return firstMoverButton;
    }

    public Button getSecondMoverButton() {
        return secondMoverButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}