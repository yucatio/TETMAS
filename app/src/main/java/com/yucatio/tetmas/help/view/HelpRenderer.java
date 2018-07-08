package com.yucatio.tetmas.help.view;

import android.opengl.GLES20;

import com.yucatio.tetmas.help.HelpScreenManager;
import com.yucatio.tetmas.io.Assets;
import com.yucatio.tetmas.io.ShaderAssets;
import com.yucatio.tetmas.texture.Animation;
import com.yucatio.tetmas.util.GLUtil;
import com.yucatio.tetmas.view.TetmasRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class HelpRenderer extends TetmasRenderer {
    private HelpScreenManager manager;

    // 頂点バッファ
    private FloatBuffer vertexBuffer;
    private static final int VERTEX_BUFFER_SIZE = 12;
    // UVバッファ
    private FloatBuffer uvBuffer;
    private static final int UV_BUFFER_SIZE = 8;

    public HelpRenderer(HelpScreenManager manager) {
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
        vertexBuffer.put(HelpLayout.background).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    @Override
    public void renderObjects() {
        GLES20.glUseProgram(ShaderAssets.oneTexShader.getProgram());
        GLUtil.checkGlError("glUseProgram");

        GLES20.glVertexAttribPointer(ShaderAssets.alphaPositionHandle, 3, GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        GLES20.glVertexAttribPointer(ShaderAssets.alphaTextureCoordHandle, 2, GLES20.GL_FLOAT, false,
                0, uvBuffer);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        Assets.helpAtlas.bind();
        renderDescription();

        GLES20.glUseProgram(ShaderAssets.alphaTexShader.getProgram());
        GLUtil.checkGlError("glUseProgram");

        GLES20.glVertexAttribPointer(ShaderAssets.alphaPositionHandle, 3, GLES20.GL_FLOAT, false,
                0, vertexBuffer);
        GLES20.glVertexAttribPointer(ShaderAssets.alphaTextureCoordHandle, 2, GLES20.GL_FLOAT, false,
                0, uvBuffer);

        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);

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

    private void renderDescription() {
        uvBuffer.put(Assets.helpRegions[manager.getCurrentPage()].textureCoord).position(0);
        vertexBuffer.put(HelpLayout.description).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderTitle() {
        uvBuffer.put(Assets.helpButton.getKeyFrame(0.0f, Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(HelpLayout.title).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }

    private void renderMenuButtons() {
        uvBuffer.put(Assets.leftArrowButton.getKeyFrame(manager.getPreviousButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(HelpLayout.previousButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        uvBuffer.put(Assets.rightArrowButton.getKeyFrame(manager.getNextButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(HelpLayout.nextButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

        uvBuffer.put(Assets.backToMenuButton.getKeyFrame(manager.getBackButton().getState(), Animation.ANIMATION_NONLOOPING).textureCoord).position(0);
        vertexBuffer.put(HelpLayout.backButton).position(0);
        drawRect(ShaderAssets.texMVPMatrixHandle);

    }
}
