package com.yucatio.tetmas.game.view;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yucatio.tetmas.game.GameWorld;
import com.yucatio.tetmas.game.attribute.FieldSize;
import com.yucatio.tetmas.game.element.Direction;
import com.yucatio.tetmas.game.element.Point;
import com.yucatio.tetmas.game.element.Tetromino;
import com.yucatio.tetmas.game.element.TetrominoPosition;
import com.yucatio.tetmas.game.state.GameEndAnimationState;
import com.yucatio.tetmas.game.state.PassAnimationState;
import com.yucatio.tetmas.game.state.StartAnimationState;
import com.yucatio.tetmas.game.state.TerritoryChangeAnimation02State;
import com.yucatio.tetmas.game.state.TerritoryChangeAnimation03State;
import com.yucatio.tetmas.game.state.TerritoryChangeAnimation04State;
import com.yucatio.tetmas.game.state.TerritoryChangeAnimation05State;
import com.yucatio.tetmas.io.Assets;
import com.yucatio.tetmas.io.ShaderAssets;
import com.yucatio.tetmas.texture.Animation;
import com.yucatio.tetmas.texture.VertexAnimation;
import com.yucatio.tetmas.util.GLUtil;
import com.yucatio.tetmas.view.TetmasRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class GameWorldRenderer extends TetmasRenderer {
    private GameWorld gameWorld;

    // 頂点バッファ
    private FloatBuffer vertexBuffer;
    private static final int VERTEX_BUFFER_SIZE = 12;
    // UVバッファ
    private FloatBuffer uvBuffer;
    private static final int UV_BUFFER_SIZE = 8;

    private Tetromino[] tetrominos = Tetromino.values();
    private Direction[] directions = Direction.values();

    // field別テトロミノ配列
    private EnumMap<FieldSize, float[]> tetorminoScaleMVPMatrix = new EnumMap<>(FieldSize.class);

    public GameWorldRenderer(GameWorld gameWorld) {
        super();

        this.gameWorld = gameWorld;

        // Buffer setting
        vertexBuffer =  ByteBuffer.allocateDirect(VERTEX_BUFFER_SIZE * FLOAT_SIZE_BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        uvBuffer =  ByteBuffer.allocateDirect(UV_BUFFER_SIZE  * FLOAT_SIZE_BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();


        for (FieldSize fieldSize : FieldSize.values()) {
            float[] tmpMvpMatrix = Arrays.copyOf(getGameMVPMatrix(), getGameMVPMatrix().length);
            Matrix.translateM(tmpMvpMatrix, 0, GameWorldLayout.fieldOffsetXMap.get(fieldSize)-GameWorldLayout.cellWidthMap.get(fieldSize)/2.0f, GameWorldLayout.fieldOffsetYMap.get(fieldSize)-GameWorldLayout.cellWidthMap.get(fieldSize)/2.0f, 0);
            Matrix.scaleM(tmpMvpMatrix, 0, GameWorldLayout.cellWidthMap.get(fieldSize), GameWorldLayout.cellWidthMap.get(fieldSize), 1.0f);
            Matrix.translateM(tmpMvpMatrix, 0, -(GameWorldLayout.fieldOffsetXMap.get(fieldSize)-GameWorldLayout.cellWidthMap.get(fieldSize)/2.0f), -(GameWorldLayout.fieldOffsetYMap.get(fieldSize)-GameWorldLayout.cellWidthMap.get(fieldSize)/2.0f), 0);

            tetorminoScaleMVPMatrix.put(fieldSize, tmpMvpMatrix);
        }

    }

    @Override
    public void renderBackground() {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUseProgram(ShaderAssets.oneTexShader.getProgram());
        GLUtil.checkGlError("glUseProgram");

        GLES20.glVertexAttribPointer(ShaderAssets.alphaPositionHandle, 3, GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        GLES20.glVertexAttribPointer(ShaderAssets.alphaTextureCoordHandle, 2, GLES20.GL_FLOAT, false,
                0, uvBuffer);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.gameBackgroundAtlas.bind();

        uvBuffer.put(Assets.gameBackground.textureCoord).position(0);
        vertexBuffer.put(GameWorldLayout.background).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    @Override
    public void renderObjects() {
        GLES20.glUseProgram(ShaderAssets.alphaTexShader.getProgram());
        GLUtil.checkGlError("glUseProgram");

        GLES20.glVertexAttribPointer(ShaderAssets.alphaPositionHandle, 3, GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        GLES20.glVertexAttribPointer(ShaderAssets.alphaTextureCoordHandle, 2, GLES20.GL_FLOAT, false,
                0, uvBuffer);

        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.tetmasFieldAtlasMap.get(gameWorld.getFieldSize()).bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.tetmasFieldAtlasAlphaMap.get(gameWorld.getFieldSize()).bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        renderField();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.gameButtonAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.gameButtonAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        renderTetrominoButtons();
        renderTetrominoNum();
        renderOKKey();
        renderControlKeys();
        renderScoreBackBround();
        renderScore();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.tetrominoAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.tetrominoAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        renderSurroundedArea();
        renderPlacedTetromino();

        if (gameWorld.getState() == TerritoryChangeAnimation02State.INSTANCE) {
            renderChangeSurroundedCellAnimation();
        }
        if (gameWorld.getState() == TerritoryChangeAnimation03State.INSTANCE) {
            renderChangeDeadCellAnimation();
        }
        if (gameWorld.getState() == TerritoryChangeAnimation04State.INSTANCE) {
            renderChangeSurroundedCellAnotherAnimation();
        }
        if (gameWorld.getState() == TerritoryChangeAnimation05State.INSTANCE) {
            renderChangeDeadCell2Animation();
        }

        GLES20.glBlendFunc(GLES20.GL_ZERO, GLES20.GL_SRC_COLOR);
        renderActiveTetromino();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.gameMessageAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.gameMessageAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        if (gameWorld.getState() == StartAnimationState.INSTANCE) {
            renderStartMessage();
        }
        if (gameWorld.getState() == GameEndAnimationState.INSTANCE) {
            renderGameEndMessage();
        }
        if (gameWorld.getState() == PassAnimationState.INSTANCE) {
            renderPassMessage();
        }

        GLES20.glDisable(GLES20.GL_BLEND);

    }

    private void renderField() {
        uvBuffer.put(Assets.tetmasFieldMap.get(gameWorld.getFieldSize()).textureCoord).position(0);
        vertexBuffer.put(GameWorldLayout.tetmasField).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderTetrominoButtons() {
        for (int i = 0; i < 2; i++) {
            for (Tetromino tetromino : tetrominos) {
                uvBuffer.put(Assets.tetrominoButtonMapList.get(i).get(tetromino).getKeyFrame(gameWorld.getTetrominoButton(i, tetromino).getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
                vertexBuffer.put(GameWorldLayout.tetrominoButtonMapList.get(i).get(tetromino)).position(0);
                drawRect(ShaderAssets.texMVPMatrixHandle);

            }
        }

        for (int i = 0; i < 2; i++) {
            uvBuffer.put(Assets.cancelButtonList.get(i).getKeyFrame(gameWorld.getCancelButton(i).getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
            vertexBuffer.put(GameWorldLayout.cancelButtonList.get(i)).position(0);
            drawRect(ShaderAssets.texMVPMatrixHandle);

        }
    }

    private void renderTetrominoNum() {
        for (int i = 0; i < 2; i++) {
            EnumMap<Tetromino, Integer> map = gameWorld.getAvailableTetromino(i);
            for (Tetromino tetromino : tetrominos) {
                int num = map.get(tetromino);
                uvBuffer.put(Assets.tetrominoNum[i][num].textureCoord).position(0);
                vertexBuffer.put(GameWorldLayout.tetrominoNum.get(i).get(tetromino)).position(0);
                drawRect(ShaderAssets.texMVPMatrixHandle);

            }
        }
    }

    private void renderOKKey() {
        uvBuffer.put(Assets.okButton.getKeyFrame(gameWorld.getOkButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(GameWorldLayout.okButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderControlKeys() {
        for (Direction direction : directions) {
            uvBuffer.put(Assets.arrowKeyMap.get(direction).getKeyFrame(gameWorld.getArrowButton(direction).getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
            vertexBuffer.put(GameWorldLayout.arrowKeyMapList.get(direction)).position(0);
            drawRect(ShaderAssets.texMVPMatrixHandle);

        }

        uvBuffer.put(Assets.spinKey.getKeyFrame(gameWorld.getSpinButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(GameWorldLayout.spinKey).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderScoreBackBround() {
        for (int i = 0; i < 2; i++) {
            uvBuffer.put(Assets.scoreRegionList.get(i).textureCoord).position(0);
            vertexBuffer.put(GameWorldLayout.scoreRegionList.get(i)).position(0);
            drawRect(ShaderAssets.texMVPMatrixHandle);
        }
    }

    private void renderScore() {
        for (int i = 0; i < 2; i++) {
            int score = gameWorld.getDisplayScore(i);

            int j = 0;
            do {
                int remainder = score % 10;
                score = score / 10;
                uvBuffer.put(Assets.numbers[remainder].textureCoord).position(0);
                vertexBuffer.put(GameWorldLayout.scoreNumberList.get(i)[j]).position(0);
                drawRect(ShaderAssets.texMVPMatrixHandle);
                j++;
            } while (score > 0);
        }
    }

    private void renderActiveTetromino() {
        TetrominoPosition tPosition = gameWorld.getActiveTetrominoPosition();

        if (tPosition == null) {
            return;
        }
        int playerIndex = gameWorld.getPlayerIndex();
        uvBuffer.put(Assets.tetrominoMapList.get(playerIndex).get(tPosition.getTetromino()).getKeyFrame(tPosition.getSpin(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(GameWorldLayout.tetrimino).position(0);
        float[] tmpmMVPMatrix = Arrays.copyOf(tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()), tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()).length);
        Matrix.translateM(tmpmMVPMatrix, 0, tPosition.getX() + GameWorldLayout.fieldOffsetXMap.get(FieldSize.TWENTY_SQUARE), tPosition.getY() + GameWorldLayout.fieldOffsetYMap.get(FieldSize.TWENTY_SQUARE), 0.0f);
        drawRect(ShaderAssets.texMVPMatrixHandle, tmpmMVPMatrix);
    }

    private void renderPlacedTetromino() {
        for (int i = 0; i < 2; i++) {
            List<TetrominoPosition> tPositions = gameWorld.getPlacedTetromino(i);
            for (TetrominoPosition tPosition : tPositions) {
                uvBuffer.put(Assets.tetrominoMapList.get(i).get(tPosition.getTetromino()).getKeyFrame(tPosition.getSpin(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
                vertexBuffer.put(GameWorldLayout.tetrimino).position(0);
                float[] tmpmMVPMatrix = Arrays.copyOf(tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()), tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()).length);
                Matrix.translateM(tmpmMVPMatrix, 0, tPosition.getX() + GameWorldLayout.fieldOffsetXMap.get(FieldSize.TWENTY_SQUARE), tPosition.getY() + GameWorldLayout.fieldOffsetYMap.get(FieldSize.TWENTY_SQUARE), 0.0f);
                drawRect(ShaderAssets.texMVPMatrixHandle, tmpmMVPMatrix);

            }
        }
    }

    private void renderSurroundedArea() {
        for (int i = 0; i < 2; i++) {
            for (Point point : gameWorld.getSurroundedCell(i)) {
                uvBuffer.put(Assets.territoty.get(i).textureCoord).position(0);
                vertexBuffer.put(GameWorldLayout.cell).position(0);
                float[] tmpmMVPMatrix = Arrays.copyOf(tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()), tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()).length);
                Matrix.translateM(tmpmMVPMatrix, 0, point.x + GameWorldLayout.fieldOffsetXMap.get(FieldSize.TWENTY_SQUARE), point.y + GameWorldLayout.fieldOffsetYMap.get(FieldSize.TWENTY_SQUARE), 0.0f);
                drawRect(ShaderAssets.texMVPMatrixHandle, tmpmMVPMatrix);

            }
        }

        for (Point point : gameWorld.getDeadCell()) {
            uvBuffer.put(Assets.deadArea.textureCoord).position(0);
            vertexBuffer.put(GameWorldLayout.cell).position(0);
            float[] tmpmMVPMatrix = Arrays.copyOf(tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()), tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()).length);
            Matrix.translateM(tmpmMVPMatrix, 0, point.x + GameWorldLayout.fieldOffsetXMap.get(FieldSize.TWENTY_SQUARE), point.y + GameWorldLayout.fieldOffsetYMap.get(FieldSize.TWENTY_SQUARE), 0.0f);
            drawRect(ShaderAssets.texMVPMatrixHandle, tmpmMVPMatrix);

        }
    }

    private void renderChangeSurroundedCellAnimation() {
        float interval = GameWorldLayout.changeCellDelay;
        List<Point> cells = gameWorld.getNewlySurroundedCell();
        for (int i = 0; i < cells.size(); i++) {
            float delay = interval * i;
            Point point = cells.get(i);
            if (gameWorld.getStateTime() - delay >= 0) {
                uvBuffer.put(Assets.territoty.get(gameWorld.getPlayerIndex()).textureCoord).position(0);
                vertexBuffer.put(GameWorldLayout.cellStateChangeAnimation.getVertex(gameWorld.getStateTime() - delay, VertexAnimation.ANIMATION_NONLOOPING)).position(0);
                float[] tmpmMVPMatrix = Arrays.copyOf(tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()), tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()).length);
                Matrix.translateM(tmpmMVPMatrix, 0, point.x + GameWorldLayout.fieldOffsetXMap.get(FieldSize.TWENTY_SQUARE), point.y + GameWorldLayout.fieldOffsetYMap.get(FieldSize.TWENTY_SQUARE), 0.0f);
                drawRect(ShaderAssets.texMVPMatrixHandle, tmpmMVPMatrix);
            }
        }
    }

    private void renderChangeSurroundedCellAnotherAnimation() {
        float interval = GameWorldLayout.changeCellDelay;
        List<Point> cells = gameWorld.getNewlySurroundedCellAnother();
        for (int i = 0; i < cells.size(); i++) {
            float delay = interval * i;
            Point point = cells.get(i);
            if (gameWorld.getStateTime() - delay >= 0) {
                uvBuffer.put(Assets.territoty.get(gameWorld.getAnotherPlayerIndex()).textureCoord).position(0);
                vertexBuffer.put(GameWorldLayout.cellStateChangeAnimation.getVertex(gameWorld.getStateTime() - delay, VertexAnimation.ANIMATION_NONLOOPING)).position(0);
                float[] tmpmMVPMatrix = Arrays.copyOf(tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()), tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()).length);
                Matrix.translateM(tmpmMVPMatrix, 0, point.x + GameWorldLayout.fieldOffsetXMap.get(FieldSize.TWENTY_SQUARE), point.y + GameWorldLayout.fieldOffsetYMap.get(FieldSize.TWENTY_SQUARE), 0.0f);
                drawRect(ShaderAssets.texMVPMatrixHandle, tmpmMVPMatrix);
            }
        }

    }

    private void renderChangeDeadCellAnimation() {
        for (Point point : gameWorld.getNewlyDeadCell()) {
            uvBuffer.put(Assets.deadArea.textureCoord).position(0);
            vertexBuffer.put(GameWorldLayout.cellStateChangeAnimation.getVertex(gameWorld.getStateTime(), VertexAnimation.ANIMATION_NONLOOPING)).position(0);
            float[] tmpmMVPMatrix = Arrays.copyOf(tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()), tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()).length);
            Matrix.translateM(tmpmMVPMatrix, 0, point.x + GameWorldLayout.fieldOffsetXMap.get(FieldSize.TWENTY_SQUARE), point.y + GameWorldLayout.fieldOffsetYMap.get(FieldSize.TWENTY_SQUARE), 0.0f);
            drawRect(ShaderAssets.texMVPMatrixHandle, tmpmMVPMatrix);

        }
    }

    private void renderChangeDeadCell2Animation() {
        for (Point point : gameWorld.getNewlyDeadCell2()) {
            uvBuffer.put(Assets.deadArea.textureCoord).position(0);
            vertexBuffer.put(GameWorldLayout.cellStateChangeAnimation.getVertex(gameWorld.getStateTime(), VertexAnimation.ANIMATION_NONLOOPING)).position(0);
            float[] tmpmMVPMatrix = Arrays.copyOf(tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()), tetorminoScaleMVPMatrix.get(gameWorld.getFieldSize()).length);
            Matrix.translateM(tmpmMVPMatrix, 0, point.x + GameWorldLayout.fieldOffsetXMap.get(FieldSize.TWENTY_SQUARE), point.y + GameWorldLayout.fieldOffsetYMap.get(FieldSize.TWENTY_SQUARE), 0.0f);
            drawRect(ShaderAssets.texMVPMatrixHandle, tmpmMVPMatrix);

        }

    }

    private void renderStartMessage() {
        uvBuffer.put(Assets.gameStartMessage.textureCoord).position(0);
        vertexBuffer.put(GameWorldLayout.gameMessage).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);
    }

    private void renderGameEndMessage() {
        uvBuffer.put(Assets.gameEndMessage.textureCoord).position(0);
        vertexBuffer.put(GameWorldLayout.gameMessage).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);
    }

    private void renderPassMessage() {
        uvBuffer.put(Assets.passMessage.textureCoord).position(0);
        vertexBuffer.put(GameWorldLayout.gameMessage).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);
    }
}
