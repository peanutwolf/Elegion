package com.vigursky.grushahit.views;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.vigursky.grushahit.MainGameSurface;
import com.vigursky.grushahit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainGameFragment extends Fragment {

    private static final String TAG = MainGameFragment.class.getSimpleName();

    public MainGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

       // this.lockOrientation();

        return new MainGameSurface(getActivity().getApplicationContext());
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
       // this.unlockOrientation();
        super.onDestroyView();
    }


    private void lockOrientation(){
        int orientation;
        int rotation = ((WindowManager) getActivity().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                break;
            case Surface.ROTATION_90:
                orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                break;
            case Surface.ROTATION_180:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                break;
            default:
                orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                break;
        }

        getActivity().setRequestedOrientation(orientation);
    }

    private void unlockOrientation(){
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

}
