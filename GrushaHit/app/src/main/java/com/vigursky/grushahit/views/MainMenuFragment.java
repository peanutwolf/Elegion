package com.vigursky.grushahit.views;


import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vigursky.grushahit.R;
import com.vigursky.grushahit.views.dialogs.SpeedSelectionDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment implements SpeedSelectionDialog.SpeedSelectionDialogListener{

    private Button gameButton;
    private Button btDeviceButton;
    private Button scoresButton;

    private static final String TAG = MainMenuFragment.class.getSimpleName();

    public MainMenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment, container, false);

        gameButton = (Button)view.findViewById(R.id.btn_game_start);
        btDeviceButton = (Button)view.findViewById(R.id.btn_btdevice_list);
        scoresButton = (Button)view.findViewById(R.id.btn_score_list);

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActionBarActivity) getActivity()).getSupportActionBar().hide();

                DialogFragment dialog = new SpeedSelectionDialog();
                dialog.setTargetFragment(MainMenuFragment.this, 0);
                dialog.show(getFragmentManager(), "SpeedSelectionDialog");
            }
        });

        btDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btDeviceButton pressed");
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();

                ft.replace(R.id.main_view_fragment, new BTDeviceFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btDeviceButton pressed");
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();

                ft.replace(R.id.main_view_fragment, new ScoresFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        return view;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int initialSpeed) {
        Log.d(TAG, "gameButton pressed");
        Bundle args = new Bundle();

        MainGameFragment gameFragment = new MainGameFragment();
        args.putInt(MainGameFragment.INITIAL_SPEED, initialSpeed);
        gameFragment.setArguments(args);

        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        ft.replace(R.id.main_view_fragment, gameFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
