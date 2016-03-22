package com.vigursky.grushahit.views;


import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vigursky.grushahit.R;
import com.vigursky.grushahit.utils.ScoreSaver;
import com.vigursky.grushahit.views.dialogs.SpeedSelectionDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment implements SpeedSelectionDialog.SpeedSelectionDialogListener{

    private Button gameButton;
    private Button btDeviceButton;
    private Button scoresButton;
    private Cursor cursor;

    private static final String TAG = MainMenuFragment.class.getSimpleName();

    public MainMenuFragment() {

    }

    void swapCursor(Cursor cursor){
        this.cursor = cursor;
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
                ScoresFragment fr = new ScoresFragment();
                fr.setCursor(cursor);

                ft.replace(R.id.main_view_fragment, fr);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        Loader<Cursor> loader = getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                String[] projection = {ScoreSaver.COLUMN_USER, ScoreSaver.COLUMN_SCORE};
                return new CursorLoader(getActivity(), ScoreSaver.CONTENT_URI, projection, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                ((MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_view_fragment)).swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

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
