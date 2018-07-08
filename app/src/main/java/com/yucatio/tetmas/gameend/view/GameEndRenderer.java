package com.yucatio.tetmas.gameend.view;

import android.opengl.GLES20;

import com.yucatio.tetmas.game.attribute.WinLossRecord;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.gameend.GameEndScreenManager;
import com.yucatio.tetmas.io.Assets;
import com.yucatio.tetmas.io.ShaderAssets;
import com.yucatio.tetmas.texture.Animation;
import com.yucatio.tetmas.util.GLUtil;
import com.yucatio.tetmas.view.TetmasRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GameEndRenderer extends TetmasRenderer{
    private GameEndScreenManager manager;

    // 頂点バッファ
    private FloatBuffer vertexBuffer;
    private static final int VERTEX_BUFFER_SIZE = 12;
    // UVバッファ
    private FloatBuffer uvBuffer;
    private static final int UV_BUFFER_SIZE = 8;

    public GameEndRenderer(GameEndScreenManager manager) {
        this.manager = manager;

        // Buffer setting
        vertexBuffer =  ByteBuffer.allocateDirect(VERTEX_BUFFER_SIZE * FLOAT_SIZE_BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        uvBuffer =  ByteBuffer.allocateDirect(UV_BUFFER_SIZE  * FLOAT_SIZE_BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

    }

    @Override
    public void renderBackground() {
        GLES20.glUseProgram(ShaderAssets.alphaTexShader.getProgram());
        GLUtil.checkGlError("glUseProgram");

        GLES20.glVertexAttribPointer(ShaderAssets.alphaPositionHandle, 3, GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        GLES20.glVertexAttribPointer(ShaderAssets.alphaTextureCoordHandle, 2, GLES20.GL_FLOAT, false,
                0, uvBuffer);

        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.backgroundScreenAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.backgroundScreenAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        uvBuffer.put(Assets.backgroundWhite80Screen.textureCoord).position(0);
        vertexBuffer.put(GameEndLayout.backScreen).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        GLES20.glDisable(GLES20.GL_BLEND);

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
        Assets.gameEndMaterialAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.gameEndMaterialAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        renderOutcome();
        renderWinLossRecord();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.gameButtonAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.gameButtonAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        renderWinLossCount();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.menuButtonAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.menuButtonAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        renderTitle();
        renderMenuButtons();

        GLES20.glDisable(GLES20.GL_BLEND);
    }

    private void renderTitle() {
        uvBuffer.put(Assets.gameEndButton.getKeyFrame(Button.ENABLE, Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(GameEndLayout.title).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        uvBuffer.put(Assets.stageButtonMap.get(manager.getStage()).getKeyFrame(Button.ENABLE, Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(GameEndLayout.stage).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderOutcome() {
        uvBuffer.put(Assets.outcomeRegionMap.get(manager.getOutcome()).textureCoord).position(0);
        vertexBuffer.put(GameEndLayout.outcome).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);
    }

    private void renderWinLossRecord() {
        uvBuffer.put(Assets.winLossRecordRegion.textureCoord).position(0);
        vertexBuffer.put(GameEndLayout.winLossRecord).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderWinLossCount() {
        WinLossRecord record = manager.getWinLossRecord();

        // win
        int count = record.getWinCount();
        int i = 0;
        do {
            int remainder = count % 10;
            count = count / 10;
            uvBuffer.put(Assets.numbers[remainder].textureCoord).position(0);
            vertexBuffer.put(GameEndLayout.winCount[i]).position(0);
            drawRect(ShaderAssets.texMVPMatrixHandle);
            i++;
        } while (count > 0);

        // even
        count = record.getEvenCount();
        i = 0;
        do {
            int remainder = count % 10;
            count = count / 10;
            uvBuffer.put(Assets.numbers[remainder].textureCoord).position(0);
            vertexBuffer.put(GameEndLayout.evenCount[i]).position(0);
            drawRect(ShaderAssets.texMVPMatrixHandle);
            i++;
        } while (count > 0);

    }

    private void renderMenuButtons() {
        uvBuffer.put(Assets.newGameButton.getKeyFrame(manager.getNewGameButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(GameEndLayout.newGameButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        uvBuffer.put(Assets.backToMenuButton.getKeyFrame(manager.getBackButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(GameEndLayout.backButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }
}
