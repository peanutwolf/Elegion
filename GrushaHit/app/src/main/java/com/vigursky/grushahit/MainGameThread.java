package com.vigursky.grushahit;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by vigursky on 18.09.2015.
 */
public class MainGameThread extends Thread {

    private final static int 	MAX_FPS = 50;
    private final static int	MAX_FRAME_SKIPS = 5;
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

    private SurfaceView surfaceView = null;
    private SurfaceHolder surfaceHolder = null;
    private static final String TAG = MainGameThread.class.getSimpleName();
    private Canvas canvas;

    public MainGameThread(SurfaceView surfaceView){
        this.surfaceView = surfaceView;
        this.surfaceHolder = surfaceView.getHolder();
    }

//    @Override
//    public void run() {
//
//        while (true){
//            canvas = surfaceHolder.lockCanvas();
//            if(canvas == null)
//                continue;
//            ((MainGameSurface)surfaceView).update();
//            ((MainGameSurface)surfaceView).render(canvas);
//            surfaceHolder.unlockCanvasAndPost(canvas);
//            try {
////                Log.d(TAG, "run");
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @Override
    public void run() {
        Canvas canvas;
        Log.d(TAG, "Starting game loop");

        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime;		// ms to sleep (<0 if we're behind)
        int framesSkipped;	// number of frames being skipped

        sleepTime = 0;

        while (true) {
            canvas = null;
            // try locking the canvas for exclusive pixel editing
            // in the surface
            try {
                canvas = this.surfaceHolder.lockCanvas();
                if(canvas == null)
                    continue;
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;	// resetting the frames skipped
                    // update game state
                    ((MainGameSurface)surfaceView).update();
                    // render state to the screen
                    // draws the canvas on the panel
                    ((MainGameSurface)surfaceView).render(canvas);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        // update without rendering
                        ((MainGameSurface)surfaceView).update();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }	// end finally
        }
    }

}
