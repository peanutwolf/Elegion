package com.vigursky.grushahit.models;

/**
 * Created by vigursky on 24.10.2015.
 */
public class UserModel {
    private String mUserName;
    private int mUserScore;

    public UserModel(String name, int score){
        mUserName = name;
        mUserScore = score;
    }

    public String getUserName(){
        return mUserName;
    }

    public int getUserScore(){
        return mUserScore;
    }
}
