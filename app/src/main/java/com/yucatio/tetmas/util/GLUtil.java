package com.yucatio.tetmas.util;

import android.opengl.GLES20;

public class GLUtil {
    public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(op + ": glError " + error);
        }
    }
}
