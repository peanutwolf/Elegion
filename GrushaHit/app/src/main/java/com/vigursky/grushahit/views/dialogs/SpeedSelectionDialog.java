package com.vigursky.grushahit.views.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vigursky.grushahit.R;

/**
 * Created by pEANUTwOLF on 06.10.2015.
 */
public class SpeedSelectionDialog extends DialogFragment {

    private int currentSpeedVal = 5;
    private SeekBar seekBar;
    private TextView speedValText;
    private SpeedSelectionDialogListener mListener;

    public interface SpeedSelectionDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, int currentSpeed);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mListener = (SpeedSelectionDialogListener)getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement SpeedSelectionDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dlg_speed_choice, null);

        builder.setView(v)
                .setPositiveButton("Let's hit it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(SpeedSelectionDialog.this, currentSpeedVal);
                    }
                });

        speedValText = (TextView)v.findViewById(R.id.txt_speed_val);
        speedValText.setText("Medium");

        seekBar = (SeekBar)v.findViewById(R.id.seek_init_speed);
        seekBar.setMax(2);
        seekBar.setProgress(1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentSpeedVal = progress;
                switch (progress) {
                    case 0:
                        speedValText.setText("Slow");
                        currentSpeedVal = 3;
                        break;
                    case 1:
                        speedValText.setText("Medium");
                        currentSpeedVal = 5;
                        break;
                    case 2:
                        speedValText.setText("Fast");
                        currentSpeedVal = 7;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return builder.create();
    }

}
