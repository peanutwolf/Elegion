package com.vigursky.activityswitcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class A extends AppCompatActivity {

    private Button nextActButton;
    private TextView actName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        nextActButton = (Button) findViewById(R.id.btn_next_act);
        actName = (TextView) findViewById(R.id.txt_act_name);

        actName.setText("Activity A");

        nextActButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(A.this, B.class);
                startActivity(i);
            }
        });

    }

}
