package com.merseyside.admin.seeworld;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;


import com.merseyside.admin.seeworld.SeeWorld.Sea;

import javax.inject.Inject;


public class GameThread extends Thread {

    @Inject Context context;
    private Canvas canvas = new Canvas();
    private Bitmap background;
    private SurfaceHolder holder;
    private Resources resources;
    @Inject Sea sea;

    private boolean runFlag = false, restartFlag = false;

    GameThread(SurfaceHolder holder){
        Application.getComponent().inject(this);
        this.holder = holder;

        prepareGame();
    }

    private void draw(SurfaceHolder holder) {
        canvas = holder.lockCanvas(null);
        Rect src = new Rect(0, 0, Constants.width, Constants.height);
        Rect dst = new Rect(0, 0, Constants.width, Constants.height);
        try {
            canvas.drawBitmap(background, src, dst, null);
        } catch (NullPointerException e){
            return;
        }
        sea.onDraw(canvas);
        if(canvas != null) {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void prepareGame(){
        this.resources = context.getResources();
        background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources,R.drawable.background), Constants.width, Constants.height,true);

        sea.setSeaInterface(new Sea.SeaInterface() {
            @Override
            public void gameStarted() {
                draw(holder);
            }

            @Override
            public boolean beastsMoved() {
                if (isInterrupted()) {
                    runFlag = true;
                    return true;
                }
                if (restartFlag) {
                    return true;
                } else {
                    draw(holder);
                    return false;
                }
            }
        });
    }


    void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        while (runFlag) {
            restartFlag = false;
            sea.newGame();
        }
    }

    public void restartGame() {
        restartFlag = true;
    }

}
