package com.peanutwolf.googleappmonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView packageText;
    private Button butPlay;
    private static String appPackageName;
    private ShakeListener mShaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        packageText = (TextView)findViewById(R.id.text_package);
        butPlay = (Button)findViewById(R.id.btn_gplay);
        butPlay.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appPackageName = extras.getString(AppReceiver.packageName);
            packageText.setText("Installed " + appPackageName);
            butPlay.setEnabled(true);
        }

        startActivity(new Intent(this, DynamicPlotXY.class));

    }

    @Override
    public void onClick(View v) {
        try {
            startActivity(new Intent(this, DynamicPlotXY.class));
        } catch (android.content.ActivityNotFoundException e) {
            System.out.println("Cannot start new activity");
        }
    }

}

class RangedLinkedList<E> extends LinkedList<E>{

    int mRange;

    RangedLinkedList(int range){
        super();
        mRange = range;
    }

    @Override
    public boolean add(E object) {
        if(this.size() >= mRange){
            this.removeFirst();
        }

        return super.add(object);
    }
}

class ShakeListener implements SensorEventListener {
    private static final int TIME_THRESHOLD = 1000;

    private SensorManager mSensorMgr;
    private long mLastTime;
    private OnShakeListener mShakeListener;
    private Context mContext;


    public interface OnShakeListener
    {
        public void onShake(float values[]);
    }

    public ShakeListener(Context context)
    {
        mContext = context;
    }

    public void setOnShakeListener(OnShakeListener listener) {
        mShakeListener = listener;
    }

    public void resume(){
        mSensorMgr = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);

        if (mSensorMgr == null){
            throw new UnsupportedOperationException("Sensors not supported");
        }
        boolean supported = false;
        try{
            supported = mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        }
        catch (Exception e){
            Toast.makeText(mContext, "Shaking not supported", Toast.LENGTH_LONG).show();
        }
        if ((!supported)&&(mSensorMgr != null)) mSensorMgr.unregisterListener(this);
    }

    public void pause(){
        if (mSensorMgr != null){
            mSensorMgr.unregisterListener(this);
            mSensorMgr = null;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    public void onSensorChanged(SensorEvent event){
        float values [] = new float[3];
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        long now = System.currentTimeMillis();

        if ((now - mLastTime) > TIME_THRESHOLD) {

            values[0] = event.values[0];
            values[1] = event.values[1];
            values[2] = event.values[2];

            mShakeListener.onShake(values);

            mLastTime = now;
        }
    }
}