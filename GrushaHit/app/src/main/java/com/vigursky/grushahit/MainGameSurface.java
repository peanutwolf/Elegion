package com.vigursky.grushahit;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vigursky.grushahit.models.MainCharacter;

/**
 * Created by vigursky on 18.09.2015.
 */
public class MainGameSurface extends SurfaceView implements
        SurfaceHolder.Callback  {

    private static final String TAG = MainGameSurface.class.getSimpleName();

    private MainGameThread mainGameThread = null;
    private ObstacleFactory obstacleFactory;
    private MainCharacter mainCharacter;

    public MainGameSurface(Context context) {
        super(context);

        this.getHolder().addCallback(this);

        mainGameThread = new MainGameThread(this);

    }

    public void update(){
        obstacleFactory.updateObstacles();
        mainCharacter.update();
    }

    public void render(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        obstacleFactory.drawObstacles(canvas);
        mainCharacter.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        mainGameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged width = " + width + " height = " + height);
        obstacleFactory = new ObstacleFactory(width, height);
        mainCharacter = new MainCharacter(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
        }
        return true;
    }
}
