package com.peanutwolf.googleappmonitor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private  int i = 0;
    private TextView packageText;
    private Button butPlay;
    private static String appPackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageText = (TextView)findViewById(R.id.text_package);
        butPlay = (Button)findViewById(R.id.btn_gplay);
        butPlay.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appPackageName = extras.getString(AppReceiver.packageName);
            packageText.setText("Installed " + appPackageName);
            butPlay.setEnabled(true);
        }

    }

    @Override
    public void onClick(View v) {
        try {
            if(appPackageName != null)
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
