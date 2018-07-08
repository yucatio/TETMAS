package com.yucatio.tetmas.winlossrecord.view;

import android.opengl.GLES20;

import com.yucatio.tetmas.game.attribute.Stage;
import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.io.Assets;
import com.yucatio.tetmas.io.ShaderAssets;
import com.yucatio.tetmas.texture.Animation;
import com.yucatio.tetmas.util.GLUtil;
import com.yucatio.tetmas.view.TetmasRenderer;
import com.yucatio.tetmas.winlossrecord.WinLossRecordScreenManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class WinLossRecordRenderer extends TetmasRenderer {
    private WinLossRecordScreenManager manager;

    // 頂点バッファ
    private FloatBuffer vertexBuffer;
    private static final int VERTEX_BUFFER_SIZE = 12;
    // UVバッファ
    private FloatBuffer uvBuffer;
    private static final int UV_BUFFER_SIZE = 8;

    public WinLossRecordRenderer(WinLossRecordScreenManager manager) {
        this.manager = manager;

        // Buffer setting
        vertexBuffer =  ByteBuffer.allocateDirect(VERTEX_BUFFER_SIZE * FLOAT_SIZE_BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        uvBuffer =  ByteBuffer.allocateDirect(UV_BUFFER_SIZE  * FLOAT_SIZE_BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

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
        Assets.backgroundAtlas.bind();

        uvBuffer.put(Assets.gameBackground.textureCoord).position(0);
        vertexBuffer.put(WinLossRecordLayout.background).position(0);
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
        Assets.winLossRecordMaterialAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.winLossRecordMaterialAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        renderWinLossRecordBox();

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
        uvBuffer.put(Assets.winLossRecordsButton.getKeyFrame(0.0f, Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(WinLossRecordLayout.title).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        switch (manager.getFieldSize()) {
            case FIFTEEN_SQUARE:
                uvBuffer.put(Assets.field15Button.getKeyFrame(Button.ENABLE, Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
                break;
            case TWENTY_SQUARE:
                uvBuffer.put(Assets.field20Button.getKeyFrame(Button.ENABLE, Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
                break;
        }

        vertexBuffer.put(WinLossRecordLayout.fieldSizeDisplay).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderWinLossRecordBox() {
        uvBuffer.put(Assets.winLossRecordAllRegion.textureCoord).position(0);
        vertexBuffer.put(WinLossRecordLayout.winLossRecordBox).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    public void renderWinLossCount() {
        Stage[] stages = {Stage.EASY, Stage.MIDDLE, Stage.HARD};
        for (Stage stage : stages) {
            int count = manager.getWinLossRecord(stage).getWinCount();
            int i = 0;
            do {
                int remainder = count % 10;
                count = count / 10;
                uvBuffer.put(Assets.numbers[remainder].textureCoord).position(0);
                vertexBuffer.put(WinLossRecordLayout.winCountMap.get(stage)[i]).position(0);
                drawRect(ShaderAssets.texMVPMatrixHandle);
                i++;
            } while (count > 0);
        }

        for (Stage stage : stages) {
            int count = manager.getWinLossRecord(stage).getEvenCount();
            int i = 0;
            do {
                int remainder = count % 10;
                count = count / 10;
                uvBuffer.put(Assets.numbers[remainder].textureCoord).position(0);
                vertexBuffer.put(WinLossRecordLayout.evenCountMap.get(stage)[i]).position(0);
                drawRect(ShaderAssets.texMVPMatrixHandle);
                i++;
            } while (count > 0);
        }

    }

    private void renderMenuButtons() {
        uvBuffer.put(Assets.leftArrowButton.getKeyFrame(manager.getPreviousButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(WinLossRecordLayout.previousButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        uvBuffer.put(Assets.rightArrowButton.getKeyFrame(manager.getNextButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(WinLossRecordLayout.nextButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        uvBuffer.put(Assets.backToMenuButton.getKeyFrame(manager.getBackButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(WinLossRecordLayout.backButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }
}
