package com.yucatio.tetmas.mainmenu;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.mainmenu.view.MainMenuLayout;
import com.yucatio.tetmas.util.OverlapTester;

public class MainMenuScreenManager {
    private Button easyLevelButton = new Button();
    private Button middleLevelButton = new Button();
    private Button hardLevelButton = new Button();
    private Button twoPlayerButton = new Button();
    private Button winLossRecordButton = new Button();
    private Button helpButton = new Button();

    private Button[] buttons = {easyLevelButton, middleLevelButton, hardLevelButton, twoPlayerButton, winLossRecordButton, helpButton};

    public MainMenuScreenManager() {
    }

    public void update(Input.TouchEvent touchEvent) {
        for (Button button : buttons) {
            button.setState(Button.ENABLE);
        }

        if (touchEvent.type != Input.TouchEvent.TOUCH_DOWN && touchEvent.type != Input.TouchEvent.TOUCH_DRAGGED) {
            return;
        }

        if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.easyLevel)) {
            easyLevelButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.middleLevel)) {
            middleLevelButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.hardLevel)) {
            hardLevelButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.twoPlayer)) {
            twoPlayerButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.winLossRecords)) {
            winLossRecordButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, MainMenuLayout.help)) {
            helpButton.setState(Button.SELECTED);
        }
    }


    public Button getEasyLevelButton() {
        return easyLevelButton;
    }

    public Button getMiddleLevelButton() {
        return middleLevelButton;
    }

    public Button getHardLevelButton() {
        return hardLevelButton;
    }

    public Button getTwoPlayerButton() {
        return twoPlayerButton;
    }

    public Button getWinLossRecordButton() {
        return winLossRecordButton;
    }

    public Button getHelpButton() {
        return helpButton;
    }

}
