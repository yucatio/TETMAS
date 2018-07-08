package com.yucatio.tetmas.io;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Pool;
import com.yucatio.tetmas.framework.PoolObjectFactory;
import com.yucatio.tetmas.game.view.GameWorldLayout;

import java.util.ArrayList;
import java.util.List;

public class SingleTouchHandler implements OnTouchListener {
    private Pool<Input.TouchEvent> touchEventPool;
    private List<Input.TouchEvent> touchEvents = new ArrayList<>();
    private List<Input.TouchEvent> touchEventBuffer = new ArrayList<>();

    private float viewToWorldScaleX = 1.0f;
    private float viewToWorldScaleY = 1.0f;
    private float viewToWorldOffsetX = 0.0f;
    private float viewToWorldOffsetY = 0.0f;

    public SingleTouchHandler(View view) {
        view.setOnTouchListener(this);
        PoolObjectFactory<Input.TouchEvent> factory =
                new PoolObjectFactory<Input.TouchEvent>() {
                    @Override
                    public Input.TouchEvent createObject() {
                        return new Input.TouchEvent();
                    }
                };
        touchEventPool = new Pool<>(factory, 100);

    }

    public boolean onTouch(View v, MotionEvent event) {
        synchronized(this) {
            Input.TouchEvent touchEvent = touchEventPool.newObject();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    touchEvent.type = Input.TouchEvent.TOUCH_DOWN;
                    break;
                case MotionEvent.ACTION_MOVE :
                    touchEvent.type = Input.TouchEvent.TOUCH_DRAGGED;
                    break;
                case MotionEvent.ACTION_CANCEL :
                    touchEvent.type = Input.TouchEvent.TOUCH_UP;
                    break;
                case MotionEvent.ACTION_UP :
                    touchEvent.type = Input.TouchEvent.TOUCH_UP;
                    break;
            }
            touchEvent.x = (int) event.getX() * viewToWorldScaleX + viewToWorldOffsetX;
            touchEvent.y = (int) event.getY() * viewToWorldScaleY + viewToWorldOffsetY;
            touchEventBuffer.add(touchEvent);

            return true;
        }
    }

    public List<Input.TouchEvent> getTouchEvents() {
        synchronized (this) {
            for (Input.TouchEvent event : touchEvents) {
                touchEventPool.free(event);
            }
            touchEvents.clear();
            touchEvents.addAll(touchEventBuffer);
            touchEventBuffer.clear();
            return touchEvents;
        }
    }

    public void onSurfaceChanged(int width, int height, int renderWidth,
                                  int renderHeight) {
        viewToWorldScaleX = GameWorldLayout.gameWorldWidth / renderWidth;
        viewToWorldScaleY = GameWorldLayout.gameWorldHeight / renderHeight;
        viewToWorldOffsetX = -(width - renderWidth) * GameWorldLayout.gameWorldWidth / renderWidth / 2.0f - GameWorldLayout.gameWorldOffsetX;
        viewToWorldOffsetY = -(height - renderHeight) * GameWorldLayout.gameWorldHeight / renderHeight / 2.0f - GameWorldLayout.gameWorldOffsetY;
    }
}
