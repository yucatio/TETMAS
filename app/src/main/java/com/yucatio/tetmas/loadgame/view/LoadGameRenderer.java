package com.yucatio.tetmas.loadgame.view;

import android.opengl.GLES20;

import com.yucatio.tetmas.game.element.Button;
import com.yucatio.tetmas.io.Assets;
import com.yucatio.tetmas.io.ShaderAssets;
import com.yucatio.tetmas.loadgame.LoadGameScreenManager;
import com.yucatio.tetmas.texture.Animation;
import com.yucatio.tetmas.util.GLUtil;
import com.yucatio.tetmas.view.TetmasRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class LoadGameRenderer extends TetmasRenderer {
    private LoadGameScreenManager manager;

    // 頂点バッファ
    private FloatBuffer vertexBuffer;
    private static final int VERTEX_BUFFER_SIZE = 12;
    // UVバッファ
    private FloatBuffer uvBuffer;
    private static final int UV_BUFFER_SIZE = 8;

    public LoadGameRenderer(LoadGameScreenManager manager) {
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

        uvBuffer.put(Assets.background.textureCoord).position(0);
        vertexBuffer.put(LoadGameLayout.background).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

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
        vertexBuffer.put(LoadGameLayout.backScreen).position(0);
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
        Assets.titleAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.titleAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        renderMessage();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.menuButtonAtlas.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureHandle, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        Assets.menuButtonAtlasAlpha.bind();
        GLES20.glUniform1i(ShaderAssets.alphaTextureAlphaHandle, 1);
        renderStage();
        renderMenuButtons();

        GLES20.glDisable(GLES20.GL_BLEND);

    }

    private void renderMessage() {
        uvBuffer.put(Assets.loadGameMessage.textureCoord).position(0);
        vertexBuffer.put(LoadGameLayout.message).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderStage() {
        uvBuffer.put(Assets.stageButtonMap.get(manager.getStage()).getKeyFrame(Button.ENABLE, Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(LoadGameLayout.stageDisplay).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        switch (manager.getFieldSize()) {
            case FIFTEEN_SQUARE:
                uvBuffer.put(Assets.field15Button.getKeyFrame(Button.ENABLE, Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
                break;
            case TWENTY_SQUARE:
                uvBuffer.put(Assets.field20Button.getKeyFrame(Button.ENABLE, Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
                break;
        }

        vertexBuffer.put(LoadGameLayout.fieldSizeDisplay).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderMenuButtons() {
        uvBuffer.put(Assets.resumeGameButton.getKeyFrame(manager.getResumeGameButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(LoadGameLayout.resumeGameButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        uvBuffer.put(Assets.resetGameButton.getKeyFrame(manager.getNewGameButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(LoadGameLayout.newGameButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        uvBuffer.put(Assets.backButton.getKeyFrame(manager.getBackButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(LoadGameLayout.backButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }
}
