package com.vigursky.grushahit.utils;

/**
 * Created by vigursky on 21.09.2015.
 */


public class Speed {

    private Direction DIRECTION_X = Direction.NO_MOVE;
    private Direction DIRECTION_Y = Direction.FORWARD;

    //Velocity over axis
    private int xv;
    private int yv;

    public Speed(){
        this.xv = 0;
        this.yv = 5;
    }

    public Speed(int xv, int yv){
        this.xv = xv;
        this.yv = yv;
    }

    public int getXv() {
        return xv;
    }
    public void setXv(int xv) {
        this.xv = xv;
    }
    public int getYv() {
        return yv;
    }
    public void setYv(int yv) {
        this.yv = yv;
    }

    public Direction getxDirection() {
        return DIRECTION_X;
    }
    public void setxDirection(Direction xDirection) {
        this.DIRECTION_X = xDirection;
    }
    public Direction getyDirection() {
        return DIRECTION_Y;
    }
    public void setyDirection(Direction yDirection) {
        this.DIRECTION_Y = yDirection;
    }

    enum Direction{
        NO_MOVE,
        FORWARD,
        BACKWARD
    }

}
