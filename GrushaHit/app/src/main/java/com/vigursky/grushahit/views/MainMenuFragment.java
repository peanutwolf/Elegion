package com.vigursky.grushahit.views;


import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vigursky.grushahit.MainGameSurface;
import com.vigursky.grushahit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {

    private Button gameButton;
    private Button btDeviceButton;

    private static final String TAG = MainMenuFragment.class.getSimpleName();

    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment, container, false);

        gameButton = (Button)view.findViewById(R.id.gameButton);
        btDeviceButton = (Button)view.findViewById(R.id.btDevicesButton);

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "gameButton pressed");
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();

                ft.replace(R.id.main_view_fragment, new MainGameFragment());
                ft.addToBackStack(null);
                ft.commit();
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
        return view;
    }


}
