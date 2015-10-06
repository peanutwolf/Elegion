package com.vigursky.grushahit;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vigursky.grushahit.utils.BTDevicePipe;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;

/**
 * Created by vigursky on 18.09.2015.
 */
public class MainGameThread extends Thread {

    private final static int 	MAX_FPS = 50;
    private final static int	MAX_FRAME_SKIPS = 5;
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    public boolean running = true;

    private static final String TAG = MainGameThread.class.getSimpleName();

    public MainGameThread(SurfaceView surfaceView){
        this.surfaceView = surfaceView;
        this.surfaceHolder = surfaceView.getHolder();
    }


    @Override
    public void run() {
        Canvas canvas;
        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;
        running = true;

        Log.d(TAG, "Starting game loop");

        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                if(canvas == null || surfaceView == null)
                    continue;
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;

                    ((MainGameSurface) surfaceView).update();
                    ((MainGameSurface)surfaceView).render(canvas);
                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        ((MainGameSurface)surfaceView).update();
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        Log.d(TAG, "Exiting game loop");
    }

}
