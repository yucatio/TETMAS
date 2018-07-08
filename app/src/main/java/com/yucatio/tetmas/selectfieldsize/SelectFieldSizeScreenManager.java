package com.yucatio.tetmas.selectfieldsize;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.selectfieldsize.view.SelectFieldSizeLayout;
import com.yucatio.tetmas.util.OverlapTester;

public class SelectFieldSizeScreenManager {
    private Stage stage;
    private Button field15Button = new Button();
    private Button field20Button = new Button();
    private Button backButton = new Button();

    private Button[] buttons = {field15Button, field20Button, backButton};

    public SelectFieldSizeScreenManager(Stage stage) {
        this.stage = stage;
    }

    public void update(Input.TouchEvent touchEvent) {
        for (Button button : buttons) {
            button.setState(Button.ENABLE);
        }

        if (touchEvent.type != Input.TouchEvent.TOUCH_DOWN && touchEvent.type != Input.TouchEvent.TOUCH_DRAGGED) {
            return;
        }

        if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectFieldSizeLayout.field15Button)) {
            field15Button.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectFieldSizeLayout.field20Button)) {
            field20Button.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, SelectFieldSizeLayout.backButton)) {
            backButton.setState(Button.SELECTED);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public Button getField15Button() {
        return field15Button;
    }

    public Button getField20Button() {
        return field20Button;
    }

    public Button getBackButton() {
        return backButton;
    }
}
