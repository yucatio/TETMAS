package com.yucatio.tetmas.io;

import android.content.Context;
import android.opengl.GLES20;

import com.yucatio.tetmas.R;
import com.yucatio.tetmas.util.GLUtil;
import com.yucatio.tetmas.view.Shader;

public class ShaderAssets {
    public static Shader oneTexShader;
    public static int texMVPMatrixHandle;
    public static int texPositionHandle;
    public static int texTextureCoordHandle;

    public static Shader alphaTexShader;
    public static int alphaMVPMatrixHandle;
    public static int alphaPositionHandle;
    public static int alphaTextureCoordHandle;
    public static int alphaTextureHandle;
    public static int alphaTextureAlphaHandle;


    public static void load(Context context) {
        oneTexShader = new Shader(R.raw.simple_texture_vs, R.raw.simple_texture_fs, context);
        alphaTexShader = new Shader(R.raw.simple_texture_vs, R.raw.texture_alpha_fs, context);

        setupHandle();
    }

    private static void setupHandle() {
        //
        // oneTexShader
        //
        texPositionHandle = GLES20.glGetAttribLocation(oneTexShader.getProgram(), "aPosition");
        GLUtil.checkGlError("glGetAttribLocation aPosition");
        if (texPositionHandle == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        texTextureCoordHandle = GLES20.glGetAttribLocation(oneTexShader.getProgram(), "aTextureCoord");
        GLUtil.checkGlError("glGetAttribLocation aTextureCoord");
        if (texTextureCoordHandle == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }

        texMVPMatrixHandle = GLES20.glGetUniformLocation(oneTexShader.getProgram(), "uMVPMatrix");
        GLUtil.checkGlError("glGetUniformLocation uMVPMatrix");
        if (texMVPMatrixHandle == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }

        // 頂点バッファの有効化
        GLES20.glEnableVertexAttribArray(texPositionHandle);
        GLUtil.checkGlError("glEnableVertexAttribArray texaPositionHandle");
        // UVバッファの有効化
        GLES20.glEnableVertexAttribArray(texTextureCoordHandle);
        GLUtil.checkGlError("glEnableVertexAttribArray texaTextureHandle");

        //
        // alphaTexShader
        //
        alphaMVPMatrixHandle = GLES20.glGetUniformLocation(alphaTexShader.getProgram(), "uMVPMatrix");
        GLUtil.checkGlError("glGetUniformLocation alphaMVPMatrixHandle");
        if (alphaMVPMatrixHandle == -1) {
            throw new RuntimeException("Could not get attrib location for alphaMVPMatrixHandle");
        }
        alphaPositionHandle = GLES20.glGetAttribLocation(alphaTexShader.getProgram(), "aPosition");
        GLUtil.checkGlError("glGetAttribLocation alphaPositionHandle");
        if (alphaPositionHandle == -1) {
            throw new RuntimeException("Could not get attrib location for alphaPositionHandle");
        }
        alphaTextureCoordHandle = GLES20.glGetAttribLocation(alphaTexShader.getProgram(), "aTextureCoord");
        GLUtil.checkGlError("glGetAttribLocation alphaTextureHandle");
        if (alphaTextureCoordHandle == -1) {
            throw new RuntimeException("Could not get attrib location for alphaTextureHandle");
        }

        alphaTextureHandle = GLES20.glGetUniformLocation(alphaTexShader.getProgram(), "sTexture");
        alphaTextureAlphaHandle = GLES20.glGetUniformLocation(alphaTexShader.getProgram(), "sTextureAlpha");

        // 頂点バッファの有効化
        GLES20.glEnableVertexAttribArray(alphaPositionHandle);
        GLUtil.checkGlError("glEnableVertexAttribArray alphaPositionHandle");
        // UVバッファの有効化
        GLES20.glEnableVertexAttribArray(alphaTextureCoordHandle);
        GLUtil.checkGlError("glEnableVertexAttribArray alphaTextureHandle");

    }

    public static void reload() {
        oneTexShader.reload();
        alphaTexShader.reload();

        setupHandle();

    }

}