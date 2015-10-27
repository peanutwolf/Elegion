package com.vigursky.grushahit.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vigursky.grushahit.BuildConfig;
import com.vigursky.grushahit.R;
import com.vigursky.grushahit.views.MainGameSurface;

/**
 * Created by vigursky on 08.10.2015.
 */
public class ScoreConfirmDialog extends DialogFragment {

    private ScoreConfirmDialogListener mListener;
    private TextView scoreView;
    private EditText nameView;
    private int score;


    public interface ScoreConfirmDialogListener{
        void onDialogPositiveClick(DialogFragment dialog, String name, int score);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mListener = (ScoreConfirmDialogListener)getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement ScoreConfirmDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dlg_score_confirm, null);

        Bundle args = getArguments();
         score = args.getInt(MainGameSurface.GAME_SCORE);

        scoreView = (TextView) v.findViewById(R.id.txt_final_score_dlg);
        nameView = (EditText) v.findViewById(R.id.edt_final_score_name);

        scoreView.setText("Your score: " + score);

        builder.setView(v)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(scoreView.getText().equals(""))
                            return;
                        mListener.onDialogPositiveClick(ScoreConfirmDialog.this, nameView.getText().toString(), score);
                    }
                });

        return builder.create();
    }
}
