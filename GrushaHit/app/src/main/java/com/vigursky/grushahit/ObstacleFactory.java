package com.vigursky.grushahit;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.vigursky.grushahit.models.Obstacle;
import com.vigursky.grushahit.models.RectObstacle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by vigursky on 18.09.2015.
 */
public class ObstacleFactory {

    private Paint paint = new Paint();
    private List<Obstacle> obstacles = new LinkedList<>();
    private int obstaclesAreaWidth;
    private int obstaclesAreaHeight;
    private int obstacleAreaOffset;
    private int obstacleMinWidth;
    private int obstacleMinHeight;
    private int obstacleMaxWidth;
    private int obstacleMaxHeight;

    ObstacleFactory(int obstaclesAreaWidth, int obstaclesAreaHeight){
        this.obstaclesAreaWidth = obstaclesAreaWidth;
        this.obstaclesAreaHeight = obstaclesAreaHeight;

        this.obstacleAreaOffset =obstaclesAreaHeight/30; // leave 10 percents of area between obstacles
        this.obstacleMinWidth = obstaclesAreaWidth/10;
        this.obstacleMinHeight = obstaclesAreaHeight/10;
        this.obstacleMaxWidth = obstaclesAreaWidth/40;
        this.obstacleMaxHeight = obstaclesAreaHeight/70;
    }

    public Obstacle getRectObstacle(int width, int height){
        return new RectObstacle(width, height);
    }

    private void genNewObstacle(){
        RectObstacle rectObstacle;
        if(hasObstacleOnTop())
            return;

        int random_width = (int) (obstacleMinWidth + (Math.random() * ((obstacleMaxWidth - obstacleMinWidth) + 1)));
        int random_height = (int) (obstacleMinHeight + (Math.random() * ((obstacleMaxHeight - obstacleMinHeight) + 1)));
        int random_xpos = (int) ((Math.random() * (obstaclesAreaWidth-random_width)));

        rectObstacle = new RectObstacle(random_width, random_height);
        rectObstacle.setPosition(random_xpos, -1 * random_height);

        obstacles.add(rectObstacle);

        return;
    }

    private void cleanupObstacles(){
        Iterator<Obstacle> it = obstacles.iterator();
        while(it.hasNext()){
            Obstacle ob = it.next();
            if(ob.getY() > obstaclesAreaHeight)
                it.remove();
        }
    }

    private boolean hasObstacleOnTop(){
        for(Obstacle ob : obstacles){
            if(ob.getY() <= obstacleAreaOffset)
                return true;
        }
        return false;
    }

    public void setObstaclesArea(int obstaclesAreaWidth, int obstaclesAreaHeight){
        this.obstaclesAreaWidth = obstaclesAreaWidth;
        this.obstaclesAreaHeight = obstaclesAreaHeight;
    }

    public void updateObstacles(){
        this.genNewObstacle();
        for(Obstacle ob : obstacles){
            ((RectObstacle)ob).stepForward();
        }
        cleanupObstacles();

    }

    public void drawObstacles(Canvas canvas){
        paint.setStrokeWidth(3);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        for(Obstacle ob : obstacles){
            canvas.drawRect(ob.getX(), ob.getY(), ((RectObstacle)ob).getWidth() + ob.getX(), ((RectObstacle)ob).getHeight() + ob.getY(), paint);
        }
    }
}
