package com.yucatio.tetmas.game.state;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.element.Direction;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.view.GameWorldLayout;
import com.yucatio.tetmas.util.OverlapTester;

public enum MoveTetrominoState implements GameState {
    INSTANCE;

    private Tetromino[] tetrominos = Tetromino.values();
    private Direction[] directions = Direction.values();

    @Override
    public void execute(GameWorld context, Input.TouchEvent touchEvent) {
        // ボタンをリセット
        context.resetTouchedButton();
        int playerIndex = context.getPlayerIndex();

        if (touchEvent.type == Input.TouchEvent.TOUCH_UP) {


            // tetrominoButton
            for (Tetromino tetromino : tetrominos) {
                if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.tetrominoButtonMapList.get(playerIndex).get(tetromino))
                        && context.isTetominoEnable(playerIndex, tetromino)
                        && tetromino != context.getActiveTetromino()) {
                    context.tetrominoSelected(tetromino);
                    return;
                }

            }

            // cancel button
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.cancelButtonList.get(playerIndex))) {
                context.cancelTetromino();
                context.disableTetrominoControlButton();
                context.changeState(SelectTetrominoState.INSTANCE);
                return;
            }

            // OKボタンが押されたかつ置ける
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.okButton)) {
                if (context.placeTetromino()) {
                    context.disableTetrominoControlButton();
                    context.changeState(TerritoryChangeAnimation01State.INSTANCE);
                    return;
                }
            }

            // 十字キー
            for (Direction direction : directions) {
                if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.arrowKeyMapList.get(direction))) {
                    // 移動
                    context.moveTetromino(direction);
                    return;
                }
            }

            // 回転
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.spinKey)) {
                //回転
                context.spinTetromino();

            }
        } else if (touchEvent.type == Input.TouchEvent.TOUCH_DOWN || touchEvent.type == Input.TouchEvent.TOUCH_DRAGGED) {
            // tetrominoButton
            for (Tetromino tetromino : tetrominos) {
                if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.tetrominoButtonMapList.get(playerIndex).get(tetromino))
                        && context.isTetominoEnable(playerIndex, tetromino)
                        && tetromino != context.getActiveTetromino()) {

                    context.tetrominoButtonTouched(tetromino);
                    return;
                }

            }

            // cancel button
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.cancelButtonList.get(playerIndex))) {
                context.cancelButtonTouched();
                return;
            }

            // OKボタンが押された
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.okButton)) {
                context.okButtonTouched();
                return;
            }

            // フィールド内が選択されたか
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.tetmasField)) {
                // 移動
                context.moveTetromino(touchEvent.x, touchEvent.y);

                return;
            }

            // 十字キー
            for (Direction direction : directions) {
                if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.arrowKeyMapList.get(direction))) {
                    // 移動
                    context.arrowKeyTouched(direction);
                    return;
                }
            }

            // 回転
            if (OverlapTester.overlapPointRectangle(touchEvent.x, touchEvent.y, GameWorldLayout.spinKey)) {
                context.spinKeyTouched();

            }

        }

    }

    @Override
    public void execute(GameWorld context, float deltaTime) {

        // nothing to do
    }

    @Override
    public String toString() {
        return "MoveTetrominoState:" + super.toString();
    }

}