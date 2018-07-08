package com.yucatio.tetmas.help;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.help.view.HelpLayout;
import com.yucatio.tetmas.io.Assets;
import com.yucatio.tetmas.util.OverlapTester;

public class HelpScreenManager {
    private int pageNum = Assets.helpRegions.length;
    private int currentPage;

    private Button previousButton = new Button();
    private Button nextButton = new Button();
    private Button backButton = new Button();
    private Button[] buttons = {previousButton, nextButton, backButton};

    public HelpScreenManager() {
        currentPage = 0;
        previousButton.setState(Button.DISABLE);
    }

    public void update(Input.TouchEvent touchEvent) {
        setButtons();

        if (touchEvent.type == Input.TouchEvent.TOUCH_DOWN || touchEvent.type == Input.TouchEvent.TOUCH_DRAGGED) {
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, HelpLayout.previousButton)
                    && previousButton.getState() != Button.DISABLE) {
                previousButton.setState(Button.SELECTED);
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, HelpLayout.nextButton)
                    && nextButton.getState() != Button.DISABLE) {
                nextButton.setState(Button.SELECTED);
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, HelpLayout.backButton)) {
                backButton.setState(Button.SELECTED);
            }
        } if (touchEvent.type == Input.TouchEvent.TOUCH_UP) {
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, HelpLayout.previousButton)
                    && previousButton.getState() != Button.DISABLE) {
                goPreviousPage();
            } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, HelpLayout.nextButton)
                    && nextButton.getState() != Button.DISABLE) {
                goNextPage();
            }
        }
    }

    private void setButtons() {
        for (Button button : buttons) {
            button.setState(Button.ENABLE);
        }

        if (currentPage <= 0) {
            previousButton.setState(Button.DISABLE);
        }
        if (currentPage >= pageNum-1) {
            nextButton.setState(Button.DISABLE);
        }

    }

    public void goNextPage() {
        currentPage = Math.min(++currentPage, pageNum-1);

        setButtons();
    }

    public void goPreviousPage() {
        currentPage = Math.max(--currentPage, 0);

        setButtons();

    }

    public int getCurrentPage() {
        return currentPage;
    }

    public Button getPreviousButton() {
        return previousButton;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}