package com.yucatio.tetmas.game;

import android.util.Log;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.game.element.Direction;
import com.yucatio.tetmas.game.element.GameField;
import com.yucatio.tetmas.game.element.Point;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;
import com.yucatio.tetmas.game.player.ComputerPlayer;
import com.yucatio.tetmas.game.player.HumanPlayer;
import com.yucatio.tetmas.game.player.Player;
import com.yucatio.tetmas.game.state.GameState;
import com.yucatio.tetmas.game.state.StartState;
import com.yucatio.tetmas.game.view.GameWorldLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class    GameWorld  implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String TAG = "GameWorld";

    public static final int PLAYER_PINK = 0;
    public static final int PLAYER_BLUE = 1;

    public static final EnumMap<FieldSize, Integer> NUM_INITIAL_TETROMINO_MAP = new EnumMap<FieldSize, Integer>(FieldSize.class) {{
        put(FieldSize.FIFTEEN_SQUARE, 3);
        put(FieldSize.TWENTY_SQUARE, 5);
    }};

    private Tetromino[] tetrominos = Tetromino.values();
    private Direction[] directions = Direction.values();

    /** ステージ情報 */
    private Stage stage;
    /** フィールドサイズ */
    private FieldSize fieldSize;
    /** 先手後手情報 */
    private boolean goFirst;

    /** 現在のプレイヤーIndex */
    private int playerIndex;
    /** プレイヤー */
    private Player[] players = new Player[2];
    /** ゲームの状態 */
    private GameState state;
    /** 状態時間 */
    private float stateTime;
    /** ゲームフィールド */
    private GameField gameField;
    /** 持ち駒 */
    private List<EnumMap<Tetromino, Integer>> availableTetromino = new ArrayList<>();

    /** 持ち駒ボタン */
    private List<EnumMap<Tetromino, Button>> tetrominoButtons = new ArrayList<>();
    /** キャンセルボタン */
    private Button[] cancelButtons = new Button[2];
    /** OK ボタン */
    private  Button okButton;
    /** 十字キー */
    private EnumMap<Direction, Button> arrowButtons = new EnumMap<>(Direction.class);
    /** スピンボタン */
    private Button spinButton;
    /** 選択されたボタン */
    private Button selectedButton;

    public GameWorld(Stage stage, FieldSize fieldSize, boolean goFirst) {
        this.stage = stage;
        this.fieldSize = fieldSize;
        this.goFirst = goFirst;
        state = StartState.INSTANCE;
    }

    public void initialize() {
        gameField = new GameField(fieldSize);

        playerIndex = PLAYER_PINK;

        if(stage == Stage.TWO_PLAYER) {
            players[PLAYER_PINK] = new HumanPlayer();
            players[PLAYER_BLUE] = new HumanPlayer();
        } else {
            if (goFirst) {
                players[PLAYER_PINK] = new HumanPlayer();
                players[PLAYER_BLUE] = new ComputerPlayer(stage, fieldSize);
            } else {
                players[PLAYER_PINK] = new ComputerPlayer(stage, fieldSize);
                players[PLAYER_BLUE] = new HumanPlayer();
            }
        }

        for (int i=0; i < 2; i++) {
            EnumMap<Tetromino, Integer> map = new EnumMap<>(Tetromino.class);
            for (Tetromino tetromino : tetrominos) {
                map.put(tetromino, NUM_INITIAL_TETROMINO_MAP.get(fieldSize));
            }
            availableTetromino.add(map);
        }


        EnumMap<Tetromino, Button> map = new EnumMap<>(Tetromino.class);
        for (Tetromino tetromino : tetrominos) {
            map.put(tetromino, new Button());
        }
        tetrominoButtons.add(map);

        map = new EnumMap<>(Tetromino.class);
        for (Tetromino tetromino : tetrominos) {
            map.put(tetromino, new Button(Button.TEMPORARY_DISABLE));
        }
        tetrominoButtons.add(map);


        cancelButtons[0] = new Button();
        cancelButtons[1] = new Button(Button.TEMPORARY_DISABLE);

        okButton = new Button(Button.DISABLE);

        for (Direction direction : directions) {
            arrowButtons.put(direction, new Button(Button.DISABLE));
        }

        spinButton = new Button(Button.DISABLE);

    }

    public void update(Input.TouchEvent touchEvent) {
        state.execute(this, touchEvent);
    }

    public void update(float deltaTime) {
        addStateTime(deltaTime);
        state.execute(this, deltaTime);
    }

    public void changeState(GameState state) {
        Log.v(TAG, "state changed to " + state.toString());
        resetStateTime();
        this.state = state;
    }

    public boolean checkTetrominoSpace() {
        return this.checkTetrominoSpace(playerIndex);
    }

    public boolean checkTetrominoSpace(int playerIndex) {
        boolean result = false;
        EnumMap<Tetromino, Integer> map = availableTetromino.get(playerIndex);
        EnumMap<Tetromino, Button> buttonMap = tetrominoButtons.get(playerIndex);
        for (Map.Entry<Tetromino, Integer> e : map.entrySet()) {
            if (e.getValue() == 0
                    || buttonMap.get(e.getKey()).getState() == Button.DISABLE) {
                continue;
            }

            if (gameField.hasSpace(e.getKey())) {
                result = true;
            } else {
                buttonMap.get(e.getKey()).setState(Button.DISABLE);
            }
        }
        return result;
    }

    public boolean isTetominoEnable(int playerIndex, Tetromino tetromino) {
        return tetrominoButtons.get(playerIndex).get(tetromino).getState() != Button.DISABLE;
    }

    public void tetrominoSelected(Tetromino tetromino) {
        // 残り数update
        Integer currentNum = availableTetromino.get(playerIndex).get(tetromino);
        availableTetromino.get(playerIndex).put(tetromino, currentNum - 1);
        if (currentNum -1 == 0) {
            tetrominoButtons.get(playerIndex).get(tetromino).setState(Button.DISABLE);
        }

        // フィールドに出す
        Tetromino previousTetromino = gameField.changeActiveTetromino(playerIndex, tetromino);
        if (previousTetromino != null) {
            // 以前のテトロミノがあれば戻す
            Integer previousNum = availableTetromino.get(playerIndex).get(previousTetromino);
            availableTetromino.get(playerIndex).put(previousTetromino, previousNum + 1);

            if (previousNum == 0) {
                tetrominoButtons.get(playerIndex).get(previousTetromino).setState(Button.ENABLE);
            }
        }
    }

    public void cancelTetromino() {
        Tetromino previousTetromino = gameField.cancelTetromino();

        Integer previousNum = availableTetromino.get(playerIndex).get(previousTetromino);
        availableTetromino.get(playerIndex).put(previousTetromino, previousNum + 1);

        if (previousNum == 0) {
            tetrominoButtons.get(playerIndex).get(previousTetromino).setState(Button.ENABLE);
        }
    }

    /**
     * テトロミノを置きます
     * @return テトロミノを置くことができたらtrue, 置けなかった場合はfalseを返します
     */
    public boolean placeTetromino() {
        List<Tetromino> playerTetromino = new ArrayList<>();
        for (Map.Entry<Tetromino, Button> e:tetrominoButtons.get(playerIndex).entrySet()) {
            if (e.getValue().getState() != Button.DISABLE) {
                playerTetromino.add(e.getKey());
            }
        }

        List<Tetromino> anotherPlayerTetromino = new ArrayList<>();
        for (Map.Entry<Tetromino, Button> e:tetrominoButtons.get(getAnotherPlayerIndex()).entrySet()) {
            if (e.getValue().getState() != Button.DISABLE) {
                anotherPlayerTetromino.add(e.getKey());
            }
        }

        boolean checkAll = false;
        if (availableTetromino.get(playerIndex).get(gameField.getActiveTetrominoPosition().getTetromino()) <= 0) {
            checkAll = true;
        }

        return gameField.placeTetromino(playerIndex, getAnotherPlayerIndex(), playerTetromino, anotherPlayerTetromino, checkAll);
    }

    /**
     * テトロミノを移動します
     * @return テトロミノを移動できたらtrue, できなかったらfalseを返します
     */
    public boolean moveTetromino(Direction direction) {
        return gameField.moveTetromino(direction);
    }

    /**
     * テトロミノを移動します
     * @return テトロミノを移動できたらtrue, できなかったらfalseを返します
     */
    public boolean moveTetromino(float x, float y) {
        // 画面座標からフィールド座標へ変換
        return gameField.moveTetromino((int)((x - GameWorldLayout.fieldOffsetXMap.get(fieldSize))/GameWorldLayout.cellWidthMap.get(fieldSize)), (int)((y - GameWorldLayout.fieldOffsetYMap.get(fieldSize))/GameWorldLayout.cellWidthMap.get(fieldSize)));
    }

    /**
     * テトロミノを回転します
     * @return テトロミノを回転することができたらtrue, できなかったらfalseを返します
     */
    public boolean spinTetromino() {
        return gameField.spinTetromino();
    }

    public void clearNewlySurroundedCell() {
        gameField.clearNewlySurroundedCell(playerIndex);
    }

    public List<Point> getNewlySurroundedCell() {
        return gameField.getNewlySurroundedCell();
    }

    public void clearNewlySurroundedCellAnother() {
        gameField.clearNewlySurroundedCellAnother(getAnotherPlayerIndex());
    }

    public List<Point> getNewlySurroundedCellAnother() {
        return gameField.getNewlySurroundedCellAnother();
    }


    public void clearNewlyDeadCell() {
        gameField.clearNewlyDeadCell();
    }

    public List<Point> getNewlyDeadCell() {
        return gameField.getNewlyDeadCell();
    }

    public void clearNewlyDeadCell2() {
        gameField.clearNewlyDeadCell2();
    }

    public List<Point> getNewlyDeadCell2() {
        return gameField.getNewlyDeadCell2();
    }

    public Stage getStage() {
        return stage;
    }

    public FieldSize getFieldSize() {
        return fieldSize;
    }

    public boolean isGoFirst() {
        return goFirst;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public int getAnotherPlayerIndex() {
        return (playerIndex + 1) % 2;
    }

    public void changePlayer() {
        // ボタンを使用不可に
        for (Button button : tetrominoButtons.get(playerIndex).values()){
            if (button.getState() == Button.ENABLE) {
                button.setState(Button.TEMPORARY_DISABLE);
            }
        }
        cancelButtons[playerIndex].setState(Button.TEMPORARY_DISABLE);

        // プレイヤー交代
        playerIndex = (playerIndex + 1) % 2;

        // ボタンを使用可能に
        for (Button button : tetrominoButtons.get(playerIndex).values()) {
            if (button.getState() == Button.TEMPORARY_DISABLE) {
                button.setState(Button.ENABLE);
            }
        }
        cancelButtons[playerIndex].setState(Button.ENABLE);

    }

    public Player getCurrentPlayer() {
        return players[playerIndex];
    }

    public void setHand() {
        players[playerIndex].setHand(gameField, playerIndex, getAnotherPlayerIndex(), availableTetromino.get(playerIndex), availableTetromino.get(getAnotherPlayerIndex()));
    }

    public TetrominoPosition getHand() {
        return players[playerIndex].getHand();
    }

    public GameState getState() {
        return state;
    }

    public void addStateTime(float deltaTime) {
        stateTime += deltaTime;
    }

    public void resetStateTime() {
        stateTime = 0.0f;
    }

    public float getStateTime() {
        return stateTime;
    }

    public Tetromino getActiveTetromino() {
        return gameField.getActiveTetrominoPosition().getTetromino();
    }

    public TetrominoPosition getActiveTetrominoPosition() {
        return gameField.getActiveTetrominoPosition();
    }

    public List<TetrominoPosition> getPlacedTetromino(int playerIndex) {
        return gameField.getPlacedTetrominoPosition(playerIndex);
    }

    public List<Point> getSurroundedCell(int playerIndex) {
        return gameField.getSurroundedCell(playerIndex);
    }

    public List<Point> getDeadCell() {
        return gameField.getDeadCell();
    }

    /**
     * 表示用スコアをupdateします
     * @return 表示用スコアが変化すればtrue, 既に目的のスコアになっていればfalse
     */
    public boolean updateDisplayScore(float deltaScore) {
        return this.updateDisplayScore(playerIndex, deltaScore);
    }

    /**
     * 表示用スコアをupdateします
     * @param playerIndex プレイヤー番号
     * @return 表示用スコアが変化すればtrue, 既に目的のスコアになっていればfalse
     */
    public boolean updateDisplayScore(int playerIndex, float deltaScore) {
        return gameField.updateDisplayScore(playerIndex, deltaScore);
    }

    public int getScore(int playerIndex) {
        return gameField.getScore(playerIndex);
    }

    public int getDisplayScore(int playerIndex) {
        return gameField.getDisplayScore(playerIndex);
    }

    public void resetTouchedButton() {
        if (selectedButton != null) {
            selectedButton.setState(Button.ENABLE);
            selectedButton = null;
        }
    }

    public EnumMap<Tetromino, Integer> getAvailableTetromino(int playerIndex) {
        return availableTetromino.get(playerIndex);
    }

    public void tetrominoButtonTouched(Tetromino tetromino) {
        resetTouchedButton();
        tetrominoButtons.get(playerIndex).get(tetromino).setState(Button.SELECTED);
        selectedButton = tetrominoButtons.get(playerIndex).get(tetromino);
    }

    public Button getTetrominoButton(int playerIndex, Tetromino tetromino) {
        return tetrominoButtons.get(playerIndex).get(tetromino);
    }

    public void cancelButtonTouched() {
        resetTouchedButton();
        cancelButtons[playerIndex].setState(Button.SELECTED);
        selectedButton = cancelButtons[playerIndex];
    }

    public Button getCancelButton(int playerIndex) {
        return cancelButtons[playerIndex];
    }

    public void enableTetrominoControlButton() {
        enableOkButton();
        enableArrowButton();
        enableSpinButton();
    }

    public void disableTetrominoControlButton() {
        disableOkButton();
        disableArrowButton();
        disableSpinButton();
    }

    public void okButtonTouched() {
        resetTouchedButton();
        okButton.setState(Button.SELECTED);
        selectedButton = okButton;
    }

    public void enableOkButton() {
        okButton.setState(Button.ENABLE);
    }

    public void disableOkButton() {
        okButton.setState(Button.DISABLE);
    }

    public Button getOkButton() {
        return okButton;
    }

    public void arrowKeyTouched(Direction direction) {
        resetTouchedButton();
        arrowButtons.get(direction).setState(Button.SELECTED);
        selectedButton = arrowButtons.get(direction);
    }

    public Button getArrowButton(Direction direction) {
        return arrowButtons.get(direction);
    }

    public void enableArrowButton() {
        for (Button arrowButton : arrowButtons.values()) {
            arrowButton.setState(Button.ENABLE);
        }
    }

    public void disableArrowButton() {
        for (Button arrowButton : arrowButtons.values()) {
            arrowButton.setState(Button.DISABLE);
        }
    }

    public void spinKeyTouched() {
        resetTouchedButton();
        spinButton.setState(Button.SELECTED);
        selectedButton = spinButton;
    }

    public Button getSpinButton() {
        return spinButton;
    }

    public void enableSpinButton() {
        spinButton.setState(Button.ENABLE);
    }

    public void disableSpinButton() {
        spinButton.setState(Button.DISABLE);
    }

}