package com.yucatio.tetmas.winlossrecord;

import android.content.Context;
import android.util.Log;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.attribute.WinLossRecord;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.io.WinLossRecordFile;
import com.yucatio.tetmas.util.OverlapTester;
import com.yucatio.tetmas.winlossrecord.view.WinLossRecordLayout;

import java.io.IOException;
import java.util.EnumMap;

public class WinLossRecordScreenManager {
    private static final String TAG = "WinLossRecordScreenMgr";

    private FieldSize fieldSize;
    private boolean isFirst;
    private boolean isLast;

    private Button previousButton = new Button();
    private Button nextButton = new Button();
    private Button backButton = new Button();

    private Button[] buttons = {previousButton, nextButton, backButton};

    private EnumMap<Stage, WinLossRecord> winLossRecordMap = new EnumMap<>(Stage.class);

    public WinLossRecordScreenManager(Context context, FieldSize fieldSize, boolean isFirst, boolean isLast) {
        this.fieldSize = fieldSize;
        this.isFirst = isFirst;
        this.isLast = isLast;

        Stage[] stages = {Stage.EASY, Stage.MIDDLE, Stage.HARD};

        for (Stage stage : stages) {
                WinLossRecordFile dataFile = new WinLossRecordFile(context, stage, fieldSize);

                WinLossRecord winLossRecord;
                try {
                    winLossRecord = dataFile.getWinLossRecord();
                } catch (IOException e) {
                    Log.e(TAG, "IOException occurred. stage=" + stage.getGameDataId(), e);
                    winLossRecord = new WinLossRecord();
                }
                winLossRecordMap.put(stage, winLossRecord);
        }

        if (isFirst) {
            previousButton.setState(Button.DISABLE);
        }
        if (isLast) {
            nextButton.setState(Button.DISABLE);
        }

    }

    public void update(Input.TouchEvent touchEvent) {
        for (Button button : buttons) {
            button.setState(Button.ENABLE);
        }

        if (isFirst) {
            previousButton.setState(Button.DISABLE);
        }
        if (isLast) {
            nextButton.setState(Button.DISABLE);
        }

        if (touchEvent.type != Input.TouchEvent.TOUCH_DOWN && touchEvent.type != Input.TouchEvent.TOUCH_DRAGGED) {
            return;
        }

        if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, WinLossRecordLayout.previousButton)
                && previousButton.getState() != Button.DISABLE) {
            previousButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, WinLossRecordLayout.nextButton)
                && nextButton.getState() != Button.DISABLE) {
            nextButton.setState(Button.SELECTED);
        } else if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, WinLossRecordLayout.backButton)) {
            backButton.setState(Button.SELECTED);
        }

    }

    public FieldSize getFieldSize() {
        return fieldSize;
    }

    public WinLossRecord getWinLossRecord(Stage stage) {
        return winLossRecordMap.get(stage);
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
