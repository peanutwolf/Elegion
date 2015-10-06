package com.vigursky.grushahit.services;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TimerTask;
import java.util.UUID;

public class BTService extends IntentService {

    private static BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothDevice mBTDevice;
    private static BluetoothSocket mSocket;
    private static boolean started = true;

    private final IBinder mBinder = new BTServiceBinder();

    private static int X = 0;
    private static int Y = 0;

    public static final String RESULT = "result";
    public static final String OP_JOYSTICK_READ = "BTService_op_joy_read";
    public static final String OP_JOYSTICK_CON = "BTService_op_joy_connect";
    public static final String BT_DEV_ADDRESS = "BTService_dev_addr";
    public static final String BT_OP_TYPE = "BTService_op_type";
    public static final String NOTIFICATION = "com.vigursky.services";
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BTService() {
        super("BTService");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public class BTServiceBinder extends Binder {
        public BTServiceBinder getService() {
            return BTServiceBinder.this;
        }

        public Integer getX(){
            return X;
        }

        public Integer getY(){
            return Y;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        started = false;
        return super.onUnbind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String mBTDevAddr = intent.getStringExtra(BT_DEV_ADDRESS);;
        String type = intent.getStringExtra(BT_OP_TYPE);

        if(type == null)
            return;

        switch (type) {
            case OP_JOYSTICK_READ:
                try {
                    joystickReadDataLoop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case OP_JOYSTICK_CON:
                joystickConnect(mBTDevAddr);
                break;
            default:
                break;
        }

    }

    private void joystickReadDataLoop() throws IOException {

        if(mSocket == null)
            return;

        started = true;

        InputStream mInputStream = mSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(mInputStream));
        String xyLine = "0/0";

        while(started){
            if(reader.ready())
                xyLine = reader.readLine();
            if(!xyLine.equals("")){
                BTService.updateXYCoordinates(xyLine);
            }
        }
    }

    private void joystickConnect(String mBTAddess){

        if(mBluetoothAdapter == null){
            this.publishResults(Activity.RESULT_CANCELED);
        }if(mSocket != null){
            if(mSocket.isConnected()){
                this.publishResults(Activity.RESULT_CANCELED);
            }
        }

        mBTDevice = mBluetoothAdapter.getRemoteDevice(mBTAddess);
        if(mBTDevice == null){
            this.publishResults(Activity.RESULT_CANCELED);
        }

        try {
            mSocket = mBTDevice.createRfcommSocketToServiceRecord(MY_UUID);
            mSocket.connect();
        } catch (IOException e) {
            try {
                mSocket.close();
            } catch (IOException e1) {}
        }

        this.publishResults(Activity.RESULT_OK);
    }

    private void publishResults(int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }


    private static void updateXYCoordinates(String xyLine){
        String xy[] = xyLine.split("/");
        try{
            setX(Integer.parseInt(xy[0]));
            setY(Integer.parseInt(xy[1]));
        }catch (NumberFormatException|ArrayIndexOutOfBoundsException e){}

    }

    private static void setX(Integer x){
        X = x;
    }

    private static void setY(Integer y){
        Y = y;
    }

}

