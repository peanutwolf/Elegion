package com.vigursky.grushahit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.vigursky.grushahit.views.MainMenuFragment;

import java.util.Set;

public class GrushaMainActivity extends ActionBarActivity {

    private static final String TAG = GrushaMainActivity.class.getSimpleName();

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_grusha_main);

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_view_fragment, new MainMenuFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        } else{
            super.onBackPressed();
        }
    }


}
