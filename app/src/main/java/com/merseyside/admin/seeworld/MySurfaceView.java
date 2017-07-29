package com.merseyside.admin.seeworld;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by ivan_ on 11.07.2017.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private GameThread game;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        game = new GameThread(surfaceHolder);
        game.setRunning(true);
        game.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        game.setRunning(false);
        game.interrupt();
        game = null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("Event", "X = " + String.valueOf(motionEvent.getX()) + " Y = " + String.valueOf(motionEvent.getY()));
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        return false;
    }

    public void restartGame() {
        game.restartGame();
    }
}
