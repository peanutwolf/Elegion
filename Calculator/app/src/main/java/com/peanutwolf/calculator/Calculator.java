package com.peanutwolf.calculator;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class Calculator extends Activity implements ServiceConnection {

    private EditText firstArg;
    private EditText secondArg;
    private Button plus;
    private Button minus;
    private Button multiply;
    private Button divide;
    private TextView result;

    private Calculate mCalcService;

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, Calculate.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        firstArg = (EditText) findViewById(R.id.firstArg);
        secondArg = (EditText) findViewById(R.id.secondArg);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        multiply = (Button) findViewById(R.id.multiply);
        divide = (Button) findViewById(R.id.divide);
        result = (TextView) findViewById(R.id.result);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator.this.doCalculate(Operations.PLUS);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator.this.doCalculate(Operations.MINUS);
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator.this.doCalculate(Operations.MULTIPLY);
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator.this.doCalculate(Operations.DIVIDE);
            }
        });
    }

    private void doCalculate(Operations op){
        try {
            Float arg1 = Float.parseFloat(String.valueOf(firstArg.getText()));
            Float arg2 = Float.parseFloat(String.valueOf(secondArg.getText()));
            if (mCalcService != null)
                result.setText(mCalcService.calculate(arg1, arg2, op));
        } catch (NumberFormatException e) {
            result.setText(getString(R.string.format_exсept));
        }
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mCalcService= ((Calculate.LocalBinder)service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mCalcService = null;
    }
}
