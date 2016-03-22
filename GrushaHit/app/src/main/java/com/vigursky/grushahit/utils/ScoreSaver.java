package com.vigursky.grushahit.utils;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.preference.PreferenceManager;

import com.vigursky.grushahit.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by vigursky on 24.10.2015.
 */
public class ScoreSaver {
    private static final String AUTHORITY = "com.vigursky.mycontentprovider";

    private static final String BASE_PATH = "users";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_SCORE = "score";

    public static void saveScore(Activity activity, String name, int score){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, name);
        values.put(COLUMN_SCORE, score+"");
        ContentProviderClient c= activity.getContentResolver().acquireContentProviderClient(CONTENT_URI);
        try {
            Uri uri = c.insert(CONTENT_URI, values);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static String readString(Activity activity, String key, String defaultValue){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        return sp.getString(key, defaultValue);
    }

    public static List<UserModel> getScoreList(Cursor cursor){
        List<UserModel> scoreList = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                String user = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER));
                String score = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCORE));
                cursor.moveToNext();
                scoreList.add(new UserModel(user,new Integer(score)));
            }

            cursor.close();
        }

        return scoreList;
    }

}
