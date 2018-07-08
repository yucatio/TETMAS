package com.yucatio.tetmas.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.yucatio.tetmas.util.GLUtil;

import java.io.IOException;
import java.io.InputStream;

public class Texture {
    private static final String TAG = "Texture";

    private Context context;
    private int imageFileId;
    private int width;
    private int height;
    private int textureId;
    private int minFilter;
    private int magFilter;
    private int repeatX;
    private int repeatY;

    public Texture(Context context, int imageFileId) {
        this.context = context;
        this.imageFileId = imageFileId;
        load();
    }

    private void load() {
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];

        InputStream is = context.getResources()
                .openRawResource(imageFileId);

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);

            this.width = bitmap.getWidth();
            this.height = bitmap.getHeight();

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
            GLUtil.checkGlError("glBindTexture");
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            GLUtil.checkGlError("texImage2D");
            setFilters(GLES20.GL_NEAREST, GLES20.GL_LINEAR);
            setRepeat(GLES20.GL_REPEAT, GLES20.GL_REPEAT);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            GLUtil.checkGlError("glBindTexture");
        } finally {
            try {
                is.close();
            } catch(IOException ignore) {
                // Ignore.
                Log.w(TAG, ignore);
            }
        }

        if (bitmap != null) {
            bitmap.recycle();
        }

    }

    public void reload() {
        load();
        bind();
        setFilters(minFilter, magFilter);
        setRepeat(repeatX, repeatY);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLUtil.checkGlError("glBindTexture");
    }

    public void setFilters(int minFilter, int magFilter) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                minFilter);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER, magFilter);

    }

    public void setRepeat(int repeatX, int repeatY) {
        this.repeatX = repeatX;
        this.repeatY = repeatY;
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                repeatX);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                repeatY);
    }

    public void bind() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
    }

    public void dispose() {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        int[] textureIds = {textureId};
        GLES20.glDeleteTextures(1, textureIds, 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
