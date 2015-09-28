package com.vigursky.grushahit.models;

import android.graphics.Color;

import com.vigursky.grushahit.models.Obstacle;
import com.vigursky.grushahit.utils.Speed;

/**
 * Created by vigursky on 18.09.2015.
 */
public class RectObstacle implements Obstacle {

    private int x;
    private int y;
    private Color color;
    private Speed speed;
    private int width;
    private int height;

    public RectObstacle(int width, int height){
        this.width = width;
        this.height = height;
        this.speed = new Speed();
    }


    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void stepForward(){
        this.x += speed.getXv();
        this.y += speed.getYv();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
