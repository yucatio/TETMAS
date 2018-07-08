package com.yucatio.tetmas.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class TetmasView extends GLSurfaceView {
   public TetmasView(Context context) {
       this(context, null);
   }

    public TetmasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
    }
}
