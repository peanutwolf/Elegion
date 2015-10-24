package com.vigursky.grushahit.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vigursky.grushahit.R;
import com.vigursky.grushahit.models.UserModel;

import java.util.List;

/**
 * Created by vigursky on 22.09.2015.
 */

public class UserScoreAdapter extends RecyclerView.Adapter<UserScoreAdapter.UserScoreHolder> {

    private List<UserModel> userMoodelList;

    public UserScoreAdapter(List<UserModel> userModels){
        this.userMoodelList = userModels;
    }

    @Override
    public UserScoreHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.score_list_item, viewGroup, false);

        return new UserScoreHolder(view);
    }

    @Override
    public void onBindViewHolder(UserScoreHolder BTDeviceHolder, int i) {
        BTDeviceHolder.onBind(userMoodelList.get(i), i);
    }

    @Override
    public int getItemCount() {
        if(userMoodelList == null)
            return 0;
        return userMoodelList.size();
    }

    class UserScoreHolder extends RecyclerView.ViewHolder {

        private TextView userID;
        private TextView userName;
        private TextView userScore;

        public UserScoreHolder(View itemView) {
            super(itemView);
            userID = (TextView)itemView.findViewById(R.id.txt_score_id);
            userName = (TextView)itemView.findViewById(R.id.txt_score_user_name);
            userScore = (TextView)itemView.findViewById(R.id.txt_score_user_score);
        }

        public void onBind(@NonNull final UserModel userModel, int id){
            userID.setText(String.valueOf(id));
            userName.setText(userModel.getUserName());
            userScore.setText(String.valueOf(userModel.getUserScore()));
        }

    }

}
