package com.vigursky.grushahit.views;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.AsyncTask;
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
import com.vigursky.grushahit.views.adapters.BTDeviceAdapter;


public class BTDeviceFragment extends Fragment implements BTDeviceAdapter.OnItemClickListener {

    private RecyclerView btDevsView;
    private BTDeviceAdapter btViewAdapter;
    private BluetoothAdapter mBluetoothAdapter;

    private static final String TAG = BTDeviceFragment.class.getSimpleName();

    public BTDeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bt_device_fragment, container, false);

        btDevsView = (RecyclerView) view.findViewById(R.id.bt_recycler_view);
        btDevsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "No bluetooth in device!!!");
            return view;
        }

        btViewAdapter = new BTDeviceAdapter(mBluetoothAdapter.getBondedDevices());
        btViewAdapter.setOnItemClickListener(this);

        btDevsView.setAdapter(btViewAdapter);

        return view;
    }

    @Override
    public void onItemClick(@NonNull BluetoothDevice btDevice) {
        Log.d(TAG, "Item clicked name = " + btDevice.getName());
    }

}



