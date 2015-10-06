package com.vigursky.ipsumswipe;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vigursky on 01.10.2015.
 */

public class IpsumFragment extends Fragment {

    private TextView ipsumText;

    private int position = 0;
    private String text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt("position");
        text = getArguments().getString("content");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fr_ipsum_view, container, false);

        ipsumText  = (TextView) view.findViewById(R.id.txt_ipsum_view);

        ipsumText.setText("Position = " + position + "\n" + text);

        return view;
    }


}
