package com.yucatio.tetmas;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.yucatio.tetmas.framework.Game;
import com.yucatio.tetmas.framework.Input;
import com.yucatio.tetmas.framework.Screen;
import com.yucatio.tetmas.io.AndroidInput;
import com.yucatio.tetmas.io.Assets;
import com.yucatio.tetmas.mainmenu.MainMenuScreen;
import com.yucatio.tetmas.view.TetmasView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TetmasActivity extends Activity implements Game, GLSurfaceView.Renderer {
    private static final String TAG = "TetmasActivity";

    private enum GLGameState {
        Initialized, Running, Paused, Finished, Idle
    }

    private TetmasView view;
    private Input input;
    private Screen screen;

    private GLGameState state;
    private final Object stateChanged = new Object();
    private final Object screenChanged = new Object();
    private boolean firstTimeCreate = true;

    private long startTime;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate start.");
        setContentView(R.layout.activity_tetmas);

        state = GLGameState.Initialized;
        startTime = System.nanoTime();

        view = (TetmasView) findViewById(R.id.tetmas_view);
        view.setRenderer(this);

        // Ad setting
        MobileAds.initialize(this, "ca-app-pub-2601595556905046~6594248813");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        input = new AndroidInput(view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume start.");

        view.onResume();

        startTime = System.nanoTime();

        // 画面ロックを防ぐ
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "onPause start.");

        if (!firstTimeCreate) {
            synchronized (stateChanged) {
                if (isFinishing()) {
                    state = GLGameState.Finished;
                } else {
                    state = GLGameState.Paused;
                }

                while (true) {
                    try {
                        stateChanged.wait();

                        break;
                    } catch (InterruptedException ignore) {
                        // nothing to do
                        Log.w(TAG, "Interrupted while waiting lock.", ignore);
                    }
                }
            }
        }

        // 画面設定のクリア
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        view.onPause();

        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            // 戻るボタン
            synchronized(screenChanged) {
                Screen currentScreen = getCurrentScreen();
                if (currentScreen != null) {
                    Screen previousScreen = currentScreen.getPreviousScreen();
                    if (previousScreen != null) {
                        // 前のscreenに遷移
                        this.setScreen(previousScreen);
                        return true;
                    }
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLGameState state;

        synchronized (stateChanged) {
            state = this.state;
        }

        if (state == GLGameState.Running) {
            synchronized(screenChanged) {
                float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
                startTime = System.nanoTime();

                screen.update(deltaTime);
                screen.present(deltaTime);
            }
        }

        if (state == GLGameState.Paused) {
            screen.pause();
            synchronized (stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }

        if (state == GLGameState.Finished) {
            screen.pause();
            screen.dispose();
            synchronized (stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.v(TAG, "onSurfaceCreated start.");

        if(firstTimeCreate) {
            Assets.load(this);

            firstTimeCreate = false;
        } else {
            Assets.reload();
        }

        synchronized (stateChanged) {
            if (state == GLGameState.Initialized) {
                screen = getStartScreen();
            }
            state = GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.v(TAG, "onSurfaceChanged start.");
        screen.onSurfaceChanged(width, height);
        input.onSurfaceChanged(width, height, screen.getRenderWidth(), screen.getRenderHeight());

    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("Screen must not null");
        }

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }

    @Override
    public Screen getStartScreen() {
        return new MainMenuScreen(this, this);
    }


}
