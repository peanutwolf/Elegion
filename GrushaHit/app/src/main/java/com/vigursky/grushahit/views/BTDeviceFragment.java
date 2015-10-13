package com.vigursky.grushahit.views;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vigursky.grushahit.R;
import com.vigursky.grushahit.services.BTService;
import com.vigursky.grushahit.views.adapters.BTDeviceAdapter;


public class BTDeviceFragment extends Fragment implements BTDeviceAdapter.OnItemClickListener {

    private RecyclerView btDevsView;
    private BTDeviceAdapter btViewAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private static ProgressDialog progressDialog;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final String TAG = BTDeviceFragment.class.getSimpleName();

    public BTDeviceFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bt_device_fragment, container, false);

        btDevsView = (RecyclerView) view.findViewById(R.id.bt_recycler_view);
        btDevsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "No bluetooth in phone!!!");
            return view;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else{
            initBTDevicesView();
        }
        return view;
    }

    @Override
    public void onItemClick(@NonNull BluetoothDevice btDevice) {
        Log.d(TAG, "Item clicked name = " + btDevice.getName());
        Intent serviceIntent = new Intent(getActivity(), BTService.class);
        serviceIntent.putExtra(BTService.BT_DEV_ADDRESS, btDevice.getAddress());
        serviceIntent.putExtra(BTService.BT_OP_TYPE, BTService.OP_JOYSTICK_CON);
        getActivity().startService(serviceIntent);

        progressDialog = ProgressDialog.show(getActivity(), "", "Please wait while connecting");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT){
            if (resultCode == Activity.RESULT_OK){
                initBTDevicesView();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter(BTService.NOTIFICATION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }


    private void initBTDevicesView(){
        btViewAdapter = new BTDeviceAdapter(mBluetoothAdapter.getBondedDevices());
        btViewAdapter.setOnItemClickListener(this);
        btDevsView.setAdapter(btViewAdapter);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode;
            String msg;
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

            Bundle bundle = intent.getExtras();
            if(progressDialog != null){
                progressDialog.dismiss();
            }
            if (bundle != null) {
                resultCode = bundle.getInt(BTService.RESULT);
                msg = bundle.getString(BTService.RESULT_MSG);
                if(msg != null){
                    dialog.setTitle(msg);
                }
                if(resultCode == Activity.RESULT_OK){
                    dialog.create().show();
                }else if(resultCode == Activity.RESULT_CANCELED){
                    dialog.setTitle("Error!").create().show();
                }
            }
        }
    };

}

