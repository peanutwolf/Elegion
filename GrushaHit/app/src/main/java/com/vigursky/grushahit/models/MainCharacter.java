package com.vigursky.grushahit.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.vigursky.grushahit.R;
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

    private Bitmap characterStraight;
    private Bitmap characterLeft;
    private Bitmap characterRight;


    public MainCharacter(Context context){
        characterStraight = BitmapFactory.decodeResource(context.getResources(), R.drawable.snail_straight);
        characterLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.snail_left);
        characterRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.snail_right);
        speed = new Speed(0, 0);
        x = 100;
        y = 100;
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
        int maxWidth = canvas.getWidth() - characterStraight.getWidth();
        int maxHeight = canvas.getHeight() - characterStraight.getHeight();

        x = x < 0 ? 0 : x;
        x = x > maxWidth ? maxWidth : x;

        y = y < 0 ? 0 : y;
        y = y > maxHeight ? maxHeight : y;

        if(speed.getXv() > 5)
            canvas.drawBitmap(characterRight, x, y, null);
        else if(speed.getXv() < -5)
            canvas.drawBitmap(characterLeft, x, y, null);
        else
            canvas.drawBitmap(characterStraight, x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rect getRectArea() {
        return new Rect(x, y, x + characterStraight.getWidth(), y + characterStraight.getHeight());
    }
}
