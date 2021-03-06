package com.vigursky.grushahit.views;



import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vigursky.grushahit.GrushaMainActivity;
import com.vigursky.grushahit.R;
import com.vigursky.grushahit.services.BTService;
import com.vigursky.grushahit.utils.ScoreSaver;
import com.vigursky.grushahit.views.dialogs.ScoreConfirmDialog;
import com.vigursky.grushahit.views.dialogs.SpeedSelectionDialog;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainGameFragment extends Fragment implements PositionUpdater, ScoreConfirmDialog.ScoreConfirmDialogListener{

    public static final String INITIAL_SPEED = "MainGameFragment_initial_speed";

    private static final String TAG = MainGameFragment.class.getSimpleName();
    private BTService.BTServiceBinder mService;
    private boolean mBound = false;
    private TextView scoreView;
    private static Handler scoreHandler;
    private int initialSpeed = 5;

    public MainGameFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fr_game_surface, container, false);
        Bundle args = getArguments();

        if(args != null && args.containsKey(INITIAL_SPEED)){
            initialSpeed = args.getInt(INITIAL_SPEED);
        }

        scoreHandler = new ScoreHandler(Looper.getMainLooper());

        MainGameSurface gameSurface = (MainGameSurface)view.findViewById(R.id.surf_main_game);
        gameSurface.setJoystickController(this);
        gameSurface.setHandler(scoreHandler);
        gameSurface.setInitialSpeed(initialSpeed);

        scoreView = (TextView) view.findViewById(R.id.txt_score);

        Intent serviceIntent = new Intent(getActivity(), BTService.class);
        serviceIntent.putExtra(BTService.BT_OP_TYPE, BTService.OP_JOYSTICK_READ);
        getActivity().startService(serviceIntent);

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        ((ActionBarActivity)getActivity()).getSupportActionBar().show();

        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), BTService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            BTService.BTServiceBinder binder = (BTService.BTServiceBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public int getX() {
        if(mBound){
            return mService.getX();
        }
        return 0;
    }

    @Override
    public int getY() {
        if(mBound){
            return mService.getY();
        }
        return 0;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name, int score) {
        ScoreSaver.saveScore(getActivity(), name, score);
        Intent intent = new Intent(getActivity(), GrushaMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private  class ScoreHandler extends Handler{
        private int score = 0;
        public ScoreHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            String type = "";
            Bundle scoreData = msg.getData();

            if(scoreData != null){
                type = scoreData.getString(MainGameSurface.MSG_TYPE);
                if(scoreData.containsKey(MainGameSurface.GAME_SCORE))
                    score = scoreData.getInt(MainGameSurface.GAME_SCORE);
            }
            switch (type){
                case MainGameSurface.SCORE_UPDATE:
                    MainGameFragment.this.scoreView.setText("Score: " + score);
                    break;
                case MainGameSurface.GAME_END:
                    DialogFragment dialog = new ScoreConfirmDialog();
                    scoreData.putInt(MainGameSurface.GAME_SCORE, score);
                    dialog.setArguments(scoreData);
                    dialog.setTargetFragment(MainGameFragment.this, 0);
                    dialog.show(getFragmentManager(), "ScoreConfirmDialog");
                    break;
                default:
                    break;
            }
        }
    }

}
