package com.vigursky.grushahit.views;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vigursky.grushahit.MainGameThread;
import com.vigursky.grushahit.R;
import com.vigursky.grushahit.models.MainCharacter;
import com.vigursky.grushahit.utils.ObstacleFactory;

/**
 * Created by vigursky on 18.09.2015.
 */

public class MainGameSurface extends SurfaceView implements
        SurfaceHolder.Callback  {

    public static final String GAME_SCORE = "GAME_SCORE";
    public static final String MSG_TYPE = "MSG_TYPE";
    public static final String SCORE_UPDATE = "SCORE_UPDATE";
    public static final String GAME_END = "GAME_END";

    private static final String TAG = MainGameSurface.class.getSimpleName();

    private Context context;

    private MainGameThread mainGameThread = null;
    private ObstacleFactory obstacleFactory;
    private MainCharacter mainCharacter;
    private PositionUpdater joystick;
    private Handler scoreHandler;
    private Message scoreMsg;
    private Bundle scoreData = new Bundle();
    private int score = 0;
    private int currentObstacleSpeed = 5;

    public MainGameSurface(Context context){
        super(context);
        this.context = context;
    }

    public MainGameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        mainGameThread = new MainGameThread(this);
        this.context = context;
    }

    public void update(){
        if(obstacleFactory == null || mainCharacter == null)
            return;
        obstacleFactory.updateObstacles();
        mainCharacter.update(joystick.getX(), joystick.getY());
        this.updateScore();
        if (obstacleFactory.isCrossover(mainCharacter.getRectArea())){
            mainGameThread.running = false;
            scoreMsg = Message.obtain();
            scoreData.putString(MSG_TYPE, GAME_END);
            scoreMsg.setData(scoreData);
            scoreHandler.sendMessage(scoreMsg);
        }
    }

    public void render(Canvas canvas) {
        if(obstacleFactory == null || mainCharacter == null|| canvas == null)
            return;
        canvas.drawColor(Color.WHITE);
        obstacleFactory.drawObstacles(canvas);
        mainCharacter.draw(canvas);
    }

    public void setJoystickController(PositionUpdater joystick){
        this.joystick = joystick;
    }

    public void updateScore(){
        score++;
        scoreMsg = Message.obtain();
        scoreData.putInt(GAME_SCORE, score);
        scoreData.putString(MSG_TYPE, SCORE_UPDATE);
        scoreMsg.setData(scoreData);
        scoreHandler.sendMessage(scoreMsg);
    }

    public void setInitialSpeed(int initialSpeed) {
        this.currentObstacleSpeed = initialSpeed;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        mainGameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged width = " + width + " height = " + height);
        obstacleFactory = new ObstacleFactory(width, height, currentObstacleSpeed);
        mainCharacter = new MainCharacter(context);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        mainGameThread.running = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
        }
        return true;
    }

    public void setHandler(Handler handler) {
        this.scoreHandler = handler;
    }
}
