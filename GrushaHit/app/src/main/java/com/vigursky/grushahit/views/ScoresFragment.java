package com.vigursky.grushahit.views;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vigursky.grushahit.R;
import com.vigursky.grushahit.utils.ScoreSaver;
import com.vigursky.grushahit.views.adapters.BTDeviceAdapter;
import com.vigursky.grushahit.views.adapters.UserScoreAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoresFragment extends Fragment {


    private RecyclerView userScoreView;
    private UserScoreAdapter userScoreAdapter;

    public ScoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_scores_list, container, false);

        userScoreView = (RecyclerView) view.findViewById(R.id.recv_score_list);
        userScoreView.setLayoutManager(new LinearLayoutManager(getActivity()));


        userScoreAdapter = new UserScoreAdapter(ScoreSaver.getScoreList(getActivity()));
        userScoreView.setAdapter(userScoreAdapter);

        return view;
    }


}
