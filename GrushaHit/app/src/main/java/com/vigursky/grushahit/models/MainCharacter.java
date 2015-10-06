package com.vigursky.grushahit.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.vigursky.grushahit.utils.Speed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        x = 10;
        y = 10;
    }

    public void update(){
        this.x += speed.getXv();
        this.y += speed.getYv();
    }

    public void update(int xv, int yv){
        speed.setXv(xv);
        speed.setYv(yv);

        this.x += speed.getXv();
        this.y += speed.getYv();
    }

    public void draw(Canvas canvas){
        int maxWidth = canvas.getWidth() - bitmap.getWidth();
        int maxHeight = canvas.getHeight() - bitmap.getHeight();

        x = x < 0 ? 0 : x;
        x = x > maxWidth ? maxWidth : x;

        y = y < 0 ? 0 : y;
        y = y > maxHeight ? maxHeight : y;
        
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rect getRectArea() {
        return new Rect(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
    }
}
