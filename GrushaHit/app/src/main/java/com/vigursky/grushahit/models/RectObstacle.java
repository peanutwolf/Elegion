package com.vigursky.grushahit.models;

import android.graphics.Color;
import android.graphics.Rect;

import com.vigursky.grushahit.utils.Speed;

/**
 * Created by vigursky on 18.09.2015.
 */
public class RectObstacle implements Obstacle {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Color color;
    private Speed speed;
    private int width;
    private int height;

    public RectObstacle(int width, int height){
        this.width = width;
        this.height = height;
        this.speed = new Speed();
    }

    public RectObstacle(int width, int height, int initialSpeed){
        this(width,height);
        this.speed.setYv(initialSpeed);
    }

    public void setPosition(int x, int y){
        this.x1 = x;
        this.y1 = y;
        this.x2 = x + width;
        this.y2 = y + height;
    }

    public void stepForward(){
        this.x1 += speed.getXv();
        this.y1 += speed.getYv();
        this.x2 = x1 + width;
        this.y2 = y1 + height;
    }

    public boolean isCrossover(Rect rect){
       return rect.intersects(x1, y1, x2, y2);
    }

    @Override
    public int getX1() {
        return x1;
    }

    @Override
    public int getY1() {
        return y1;
    }

    @Override
    public int getX2() {
        return x2;
    }

    @Override
    public int getY2() {
        return y2;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
