package com.vigursky.grushahit.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vigursky.grushahit.models.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigursky on 24.10.2015.
 */
public class ScoreSaver {

    public static final String mScoreSize = "SCORE_STORAGE_SIZE";
    public static final String mUserName = "SCORE_USER_NAME";
    public static final String mScoreValue = "SCORE_VALUE";

    public static void saveScore(Activity activity, String name, int score){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();
        int size = sp.getInt(mScoreSize, 0)+1;

        editor.putString(mUserName + size, name);
        editor.putInt(mScoreValue+size, score);
        editor.putInt(mScoreSize, size);
        editor.commit();
    }

    public static String readString(Activity activity, String key, String defaultValue){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        return sp.getString(key, defaultValue);
    }

    public static List<UserModel> getScoreList(Activity activity){
        List<UserModel> scoreList = new ArrayList<>();
        String name;
        int score;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        int size = sp.getInt(mScoreSize, 0);

        for (int i = 1; i <= size; i++) {
            name = sp.getString(mUserName + i, null);
            score = sp.getInt(mScoreSize + i, 0);
            scoreList.add(new UserModel(name, score));
        }

        return scoreList;
    }

}
