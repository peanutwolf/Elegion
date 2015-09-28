package com.vigursky.activityswitcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class C extends AppCompatActivity {

    private Button nextActButton;
    private Button backActButton;
    private TextView actName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        nextActButton = (Button) findViewById(R.id.btn_next_act);
        backActButton = (Button) findViewById(R.id.btn_back_act);
        actName = (TextView) findViewById(R.id.txt_act_name);

        actName.setText("Activity C");

        nextActButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(C.this, D.class);
                startActivity(i);
            }
        });

        backActButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(C.this, B.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }

}
