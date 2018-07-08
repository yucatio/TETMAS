package com.yucatio.tetmas.io;

import android.content.Context;

import com.yucatio.tetmas.R;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.attribute.Outcome;
import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.element.Direction;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.texture.Animation;
import com.yucatio.tetmas.texture.Texture;
import com.yucatio.tetmas.texture.TextureRegion;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Assets {
    public static Texture backgroundAtlas;
    public static TextureRegion background;

    public static Texture backgroundScreenAtlas;
    public static Texture backgroundScreenAtlasAlpha;
    public static TextureRegion backgroundWhite80Screen;

    public static Texture gameBackgroundAtlas;
    public static TextureRegion gameBackground;

    public static Texture titleAtlas;
    public static Texture titleAtlasAlpha;
    public static TextureRegion tetmasTitle;
    public static TextureRegion loadGameMessage;
    public static TextureRegion selectTurnMessage;
    public static TextureRegion selectFieldSizeMessage;

    public static Texture menuButtonAtlas;
    public static Texture menuButtonAtlasAlpha;
    public static EnumMap<Stage, Animation> stageButtonMap = new EnumMap<>(Stage.class);
    public static Animation winLossRecordsButton;
    public static Animation helpButton;
    public static Animation backToMenuButton;
    public static Animation resumeGameButton;
    public static Animation resetGameButton;
    public static Animation firstMoverButton;
    public static Animation secondMoverButton;
    public static Animation newGameButton;
    public static Animation field15Button;
    public static Animation field20Button;
    public static Animation backButton;
    public static Animation gameEndButton;
    public static Animation leftArrowButton;
    public static Animation rightArrowButton;

    public static EnumMap<FieldSize, Texture> tetmasFieldAtlasMap = new EnumMap<>(FieldSize.class);
    public static EnumMap<FieldSize, Texture> tetmasFieldAtlasAlphaMap = new EnumMap<>(FieldSize.class);
    public static EnumMap<FieldSize, TextureRegion> tetmasFieldMap = new EnumMap<>(FieldSize.class);

    public static Texture gameButtonAtlas;
    public static Texture gameButtonAtlasAlpha;
    public static List<EnumMap<Tetromino, Animation>> tetrominoButtonMapList = new ArrayList<>();
    public static TextureRegion[][] tetrominoNum = new TextureRegion[2][];
    public static List<Animation> cancelButtonList = new ArrayList<>();
    public static List<TextureRegion> scoreRegionList = new ArrayList<>();
    public static Animation okButton;
    public static EnumMap<Direction, Animation> arrowKeyMap = new EnumMap<>(Direction.class);
    public static Animation spinKey;
    public static TextureRegion[] numbers = new TextureRegion[10];

    public static Texture tetrominoAtlas;
    public static Texture tetrominoAtlasAlpha;
    public static List<EnumMap<Tetromino, Animation>> tetrominoMapList = new ArrayList<>();
    public static List<TextureRegion> territoty = new ArrayList<>();
    public static TextureRegion deadArea;

    public static Texture gameMessageAtlas;
    public static Texture gameMessageAtlasAlpha;
    public static TextureRegion gameStartMessage;
    public static TextureRegion gameEndMessage;
    public static TextureRegion passMessage;

    public static Texture gameEndMaterialAtlas;
    public static Texture gameEndMaterialAtlasAlpha;
    public static EnumMap<Outcome, TextureRegion> outcomeRegionMap = new EnumMap<>(Outcome.class);
    public static TextureRegion[] outcomeTwoPlayer = new TextureRegion[3];
    public static TextureRegion winLossRecordRegion;

    public static Texture winLossRecordMaterialAtlas;
    public static Texture winLossRecordMaterialAtlasAlpha;
    public static TextureRegion winLossRecordAllRegion;

    public static Texture helpAtlas;
    public static TextureRegion[] helpRegions = new TextureRegion[4];

    public static void load(Context context) {
        ShaderAssets.load(context);

        backgroundAtlas = new Texture(context, R.drawable.background);
        background = new TextureRegion(backgroundAtlas, 0, 0, 544, 640);

        backgroundScreenAtlas = new Texture(context, R.drawable.background_screen);
        backgroundScreenAtlasAlpha = new Texture(context, R.drawable.background_screen_alpha);
        backgroundWhite80Screen = new TextureRegion(backgroundScreenAtlas, 0, 0, 16, 16);

        gameBackgroundAtlas = new Texture(context, R.drawable.game_background);
        gameBackground = new TextureRegion(gameBackgroundAtlas, 0, 0, 544, 640);

        titleAtlas = new Texture(context, R.drawable.titles);
        titleAtlasAlpha = new Texture(context, R.drawable.titles_alpha);

        tetmasTitle = new TextureRegion(titleAtlas, 0, 0, 384, 96);
        loadGameMessage = new TextureRegion(titleAtlas, 0, 128, 320, 128);
        selectTurnMessage = new TextureRegion(titleAtlas, 0, 256, 320, 128);
        selectFieldSizeMessage = new TextureRegion(titleAtlas, 0, 386, 320, 128);

        menuButtonAtlas = new Texture(context, R.drawable.menu_buttons);
        menuButtonAtlasAlpha = new Texture(context, R.drawable.menu_buttons_alpha);
        int width = 288; int height = 60;
        int x = 0; int y = 0;
        int xInterval = 320; int yInterval = 64;
        stageButtonMap.put(Stage.EASY,
                new Animation(1.0f,
                    new TextureRegion(menuButtonAtlas, x, y, width, height),
                    new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height)));
        y += yInterval;
        stageButtonMap.put(Stage.MIDDLE,
                new Animation(1.0f,
                        new TextureRegion(menuButtonAtlas, x, y, width, height),
                        new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height)));
        y += yInterval;
        stageButtonMap.put(Stage.HARD,
                new Animation(1.0f,
                        new TextureRegion(menuButtonAtlas, x, y, width, height),
                        new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height)));
        y += yInterval;
        stageButtonMap.put(Stage.TWO_PLAYER,
                new Animation(1.0f,
                        new TextureRegion(menuButtonAtlas, x, y, width, height),
                        new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height)));
        y += yInterval;
        winLossRecordsButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        helpButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        backToMenuButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        resumeGameButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        resetGameButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        firstMoverButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        secondMoverButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        newGameButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        field15Button = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        field20Button = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;
        backButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));
        y += yInterval;

        gameEndButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x+xInterval, y, width, height));

        x = 640; y = 0;
        width = 144;
        leftArrowButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x, y+yInterval, width, height),
                new TextureRegion(menuButtonAtlas, x, y+2*yInterval, width, height));
        x += width;
        rightArrowButton = new Animation(1.0f,
                new TextureRegion(menuButtonAtlas, x, y, width, height),
                new TextureRegion(menuButtonAtlas, x, y+yInterval, width, height),
                new TextureRegion(menuButtonAtlas, x, y+2*yInterval, width, height));

        tetmasFieldAtlasMap.put(FieldSize.FIFTEEN_SQUARE, new Texture(context, R.drawable.tetmas_field_15_square));
        tetmasFieldAtlasMap.put(FieldSize.TWENTY_SQUARE, new Texture(context, R.drawable.tetmas_field_20_square));
        tetmasFieldAtlasAlphaMap.put(FieldSize.FIFTEEN_SQUARE, new Texture(context, R.drawable.tetmas_field_15_square_alpha));
        tetmasFieldAtlasAlphaMap.put(FieldSize.TWENTY_SQUARE, new Texture(context, R.drawable.tetmas_field_20_square_alpha));

        x = 0; y = 0;
        width = 512; height = 512;
        tetmasFieldMap.put(FieldSize.FIFTEEN_SQUARE, new TextureRegion(tetmasFieldAtlasMap.get(FieldSize.FIFTEEN_SQUARE), x, y, width, height));
        width = 672; height = 672;
        tetmasFieldMap.put(FieldSize.TWENTY_SQUARE, new TextureRegion(tetmasFieldAtlasMap.get(FieldSize.TWENTY_SQUARE), x, y, width, height));


        Tetromino[] tetrominoArray = Tetromino.values();

        gameButtonAtlas = new Texture(context, R.drawable.game_buttons);
        gameButtonAtlasAlpha = new Texture(context, R.drawable.game_buttons_alpha);

        xInterval = 64; yInterval = 96;
        int xPlayerInterval = 192;
        width = 54; height = 84;
        x = 0;
        for (int  i = 0; i < 2; i ++) {
            EnumMap<Tetromino, Animation> animationEnumMap = new EnumMap<>(Tetromino.class);
            y = 0;
            for (Tetromino tetromino : tetrominoArray) {
                animationEnumMap.put(tetromino,
                        new Animation(1.0f,
                                new TextureRegion(gameButtonAtlas, x, y, width, height),
                                new TextureRegion(gameButtonAtlas, x + xInterval, y, width, height),
                                new TextureRegion(gameButtonAtlas, x + 2 * xInterval, y, width, height)
                        ));
                y += yInterval;
            }
            tetrominoButtonMapList.add(animationEnumMap);
            x += xPlayerInterval;
        }

        x = 0; y = 672;
        cancelButtonList.add(new Animation(1.0f,
                new TextureRegion(gameButtonAtlas, x, y, width, height),
                new TextureRegion(gameButtonAtlas, x + xInterval, y, width, height),
                new TextureRegion(gameButtonAtlas, x + 2 * xInterval, y, width, height)));
        x += xPlayerInterval;
        cancelButtonList.add(new Animation(1.0f,
                new TextureRegion(gameButtonAtlas, x, y, width, height),
                new TextureRegion(gameButtonAtlas, x + xInterval, y, width, height),
                new TextureRegion(gameButtonAtlas, x + 2 * xInterval, y, width, height)));

        x = 0; y = 896;
        width = 64; height = 48;
        scoreRegionList.add(new TextureRegion(gameButtonAtlas, x, y, width, height));
        x += 64;
        scoreRegionList.add(new TextureRegion(gameButtonAtlas, x, y, width, height));

        yInterval = 64;
        width = 132; height = 54;
        x = 384; y = 0;
        okButton = new Animation(1.0f,
                new TextureRegion(gameButtonAtlas, x, y, width, height),
                new TextureRegion(gameButtonAtlas, x, y + yInterval, width, height),
                new TextureRegion(gameButtonAtlas, x, y + 2 * yInterval, width, height));

        Direction[] directionArray = {Direction.TOP, Direction.BOTTOM, Direction.LEFT, Direction.RIGHT};
        xInterval = 64; yInterval = 32;
        width = 48; height = 31;
        x = 384; y = 192;
        for (Direction direction : directionArray) {
            arrowKeyMap.put(direction,
                    new Animation(1.0f,
                            new TextureRegion(gameButtonAtlas, x, y, width, height),
                            new TextureRegion(gameButtonAtlas, x, y + yInterval, width, height),
                            new TextureRegion(gameButtonAtlas, x, y + 2 * yInterval, width, height)));

            x += xInterval;
        }

        width = 43; height = 30;
        x = 640; y = 192;
        spinKey = new Animation(1.0f,
                new TextureRegion(gameButtonAtlas, x, y, width, height),
                new TextureRegion(gameButtonAtlas, x, y + yInterval, width, height),
                new TextureRegion(gameButtonAtlas, x, y + 2 * yInterval, width, height));

        xInterval = 64;
        width = 64; height = 64;
        x = 0; y = 768;
        tetrominoNum[0] = new TextureRegion[7];
        for (int i = 0; i < tetrominoNum[0].length; i++) {
            tetrominoNum[0][i] = new TextureRegion(gameButtonAtlas, x, y, width, height);
            x += xInterval;
        }

        x = 0; y = 832;
        tetrominoNum[1] = new TextureRegion[7];
        for (int i = 0; i < tetrominoNum[1].length; i++) {
            tetrominoNum[1][i] = new TextureRegion(gameButtonAtlas, x, y, width, height);
            x += xInterval;
        }



        xInterval = 22;
        width = 22; height = 32;
        x = 576; y = 0;
        for (int i = 0; i < 10; i++) {
            numbers[i] = new TextureRegion(gameButtonAtlas, x, y, width, height);
            x += xInterval;
        }


        tetrominoAtlas = new Texture(context, R.drawable.tetromino);
        tetrominoAtlasAlpha = new Texture(context, R.drawable.tetromino_alpha);

        xInterval = 128; yInterval = 128;
        xPlayerInterval = 512;
        width = 128; height = 128;
        x = 0;
        for (int  i = 0; i < 2; i ++) {
            EnumMap<Tetromino, Animation> animationEnumMap = new EnumMap<>(Tetromino.class);
            y = 0;
            for (Tetromino tetromino : tetrominoArray) {
                animationEnumMap.put(tetromino,
                        new Animation(1.0f,
                                new TextureRegion(tetrominoAtlas, x, y, width, height),
                                new TextureRegion(tetrominoAtlas, x + xInterval, y, width, height),
                                new TextureRegion(tetrominoAtlas, x + 2 * xInterval, y, width, height),
                                new TextureRegion(tetrominoAtlas, x + 3 * xInterval, y, width, height)
                        ));
                y += yInterval;
            }
            tetrominoMapList.add(animationEnumMap);
            x += xPlayerInterval;
        }

        xInterval = 32;
        width = 32; height = 32;
        x = 0; y=896;

        territoty.add(new TextureRegion(tetrominoAtlas, x, y, width, height));
        x += xInterval;
        territoty.add(new TextureRegion(tetrominoAtlas, x, y, width, height));
        x += xInterval;
        deadArea = new TextureRegion(tetrominoAtlas, x, y, width, height);

        gameMessageAtlas = new Texture(context, R.drawable.game_message);
        gameMessageAtlasAlpha = new Texture(context, R.drawable.game_message_alpha);

        width = 256; height = 128;
        x = 0; y=0;
        yInterval = 128;
        gameStartMessage = new TextureRegion(gameMessageAtlas, x, y, width, height);
        y += yInterval;
        gameEndMessage = new TextureRegion(gameMessageAtlas, x, y, width, height);
        y += yInterval;
        passMessage = new TextureRegion(gameMessageAtlas,x ,y, width, height);

        gameEndMaterialAtlas = new Texture(context, R.drawable.game_end_material);
        gameEndMaterialAtlasAlpha = new Texture(context, R.drawable.game_end_material_alpha);

        yInterval = 128;
        width = 384; height = 128;
        x = 0; y=0;

        outcomeRegionMap.put(Outcome.WIN, new TextureRegion(gameEndMaterialAtlas, x, y, width, height));
        y += yInterval;
        outcomeRegionMap.put(Outcome.LOSE, new TextureRegion(gameEndMaterialAtlas, x, y, width, height));
        y += yInterval;
        outcomeRegionMap.put(Outcome.DRAW, new TextureRegion(gameEndMaterialAtlas, x, y, width, height));
        y += yInterval;
        outcomeTwoPlayer[0] = new TextureRegion(gameEndMaterialAtlas, x, y, width, height);
        y += yInterval;
        outcomeTwoPlayer[1] = new TextureRegion(gameEndMaterialAtlas, x, y, width, height);
        outcomeTwoPlayer[2] = outcomeRegionMap.get(Outcome.DRAW);

        x = 512; y = 0;
        width = 384; height = 192;
        winLossRecordRegion = new TextureRegion(gameEndMaterialAtlas, x, y, width, height);

        winLossRecordMaterialAtlas = new Texture(context, R.drawable.win_loss_records_material);
        winLossRecordMaterialAtlasAlpha = new Texture(context, R.drawable.win_loss_records_material_alpha);

        x =0; y = 0;
        width = 448; height = 384;
        winLossRecordAllRegion = new TextureRegion(winLossRecordMaterialAtlas, x, y, width, height);

        helpAtlas = new Texture(context, R.drawable.help);
        x =0; y = 0;
        width = 448; height = 512;
        xInterval = 448;
        helpRegions[0] = new TextureRegion(helpAtlas, x, y, width, height);
        x += xInterval;
        helpRegions[1] = new TextureRegion(helpAtlas, x, y, width, height);
        x =0; y = 512;
        helpRegions[2] = new TextureRegion(helpAtlas, x, y, width, height);
        x += xInterval;
        helpRegions[3] = new TextureRegion(helpAtlas, x, y, width, height);
    }

    public static void reload() {
        ShaderAssets.reload();

        backgroundAtlas.reload();

        backgroundScreenAtlas.reload();
        backgroundScreenAtlasAlpha.reload();

        gameBackgroundAtlas.reload();

        titleAtlas.reload();
        titleAtlasAlpha.reload();

        menuButtonAtlas.reload();
        menuButtonAtlasAlpha.reload();

        for (Texture texture : tetmasFieldAtlasMap.values()) {
            texture.reload();
        }
        for (Texture texture : tetmasFieldAtlasAlphaMap.values()) {
            texture.reload();
        }

        gameButtonAtlas.reload();
        gameButtonAtlasAlpha.reload();

        tetrominoAtlas.reload();
        tetrominoAtlasAlpha.reload();

        gameMessageAtlas.reload();
        gameMessageAtlasAlpha.reload();

        gameEndMaterialAtlas.reload();
        gameEndMaterialAtlasAlpha.reload();

        winLossRecordMaterialAtlas.reload();
        winLossRecordMaterialAtlasAlpha.reload();

        helpAtlas.reload();
    }
}
