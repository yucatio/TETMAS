package com.yucatio.tetmas.view;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.yucatio.tetmas.util.GLUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Shader {
    private static final String TAG = "Shader";

    private String vertexSource;
    private String fragmentSource;

    private int mProgram;

    public Shader(int vsId, int fsId, Context context) {
        StringBuilder vsBuilder = new StringBuilder();
        StringBuilder fsBuilder = new StringBuilder();

        // read the files
        InputStream inputStream = null;
        BufferedReader in = null;
        try {
            // vertex shader
            inputStream = context.getResources().openRawResource(vsId);
            in = new BufferedReader(new InputStreamReader(inputStream));

            String read = in.readLine();
            while (read != null) {
                vsBuilder.append(read).append("\n");
                read = in.readLine();
            }
            vsBuilder.deleteCharAt(vsBuilder.length() - 1);

            // fragment shader
            inputStream = context.getResources().openRawResource(fsId);
            in = new BufferedReader(new InputStreamReader(inputStream));

            read = in.readLine();
            while (read != null) {
                fsBuilder.append(read).append("\n");
                read = in.readLine();
            }
            fsBuilder.deleteCharAt(fsBuilder.length() - 1);
        } catch (IOException e) {
            Log.e("ERROR-readingShader", "Could not read shader: " + e.getLocalizedMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, "faild to close BufferedReader.", e);
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "faild to close InputStream.", e);
                }
            }
        }

        vertexSource = vsBuilder.toString();
        fragmentSource = fsBuilder.toString();

        mProgram = createProgram(vertexSource, fragmentSource);
    }

    private int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }

        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            GLUtil.checkGlError("glAttachShader:vertexShader");
            GLES20.glAttachShader(program, pixelShader);
            GLUtil.checkGlError("glAttachShader:pixelShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program.");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    public int getProgram() {
        return mProgram;
    }

    public void reload() {
        createProgram(vertexSource, fragmentSource);
    }
}
