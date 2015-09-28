package com.vigursky.grushahit.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.vigursky.grushahit.utils.Speed;

/**
 * Created by vigursky on 23.09.2015.
 */
public class MainCharacter {

    private int x;
    private int y;
    private Speed speed;
    private int width;
    private int height;
    private Bitmap bitmap;

    public MainCharacter(Bitmap bitmap){
        this.bitmap = bitmap;
        speed = new Speed(0, 0);
        x = 100;
        y = 100;
    }

    public void update(){
        this.x += speed.getXv();
        this.y += speed.getYv();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
