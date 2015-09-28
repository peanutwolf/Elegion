package com.vigursky.grushahit.views.adapters;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vigursky.grushahit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by vigursky on 22.09.2015.
 */

public class BTDeviceAdapter extends RecyclerView.Adapter<BTDeviceAdapter.BTDeviceHolder> {

    private List<BluetoothDevice> btList;
    private OnItemClickListener mOnItemClickListener;

    public BTDeviceAdapter(Set<BluetoothDevice> btSet){
        this.btList = new ArrayList<>(btSet);
    }

    @Override
    public BTDeviceHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bt_device_item, viewGroup, false);

        return new BTDeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(BTDeviceHolder BTDeviceHolder, int i) {
        BTDeviceHolder.onBind(btList.get(i));
    }

    @Override
    public int getItemCount() {
        if(btList == null)
            return 0;
        return btList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    class BTDeviceHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView btDevName;
        private TextView btDevAddr;
        private BluetoothDevice btDevice;

        public BTDeviceHolder(View itemView) {
            super(itemView);
            btDevName = (TextView)itemView.findViewById(R.id.txt_bt_dev_name);
            btDevAddr = (TextView)itemView.findViewById(R.id.txt_bt_mac_addr);
            itemView.setOnClickListener(this);
        }

        public void onBind(@NonNull final BluetoothDevice btDevice){
            this.btDevice = btDevice;
            btDevName.setText(btDevice.getName());
            btDevAddr.setText(btDevice.getAddress());
        }

        @Override
        public void onClick(View v){
            mOnItemClickListener.onItemClick(btDevice);
        }

    }

    public interface OnItemClickListener{
        void onItemClick(@NonNull BluetoothDevice btDevice);
    }
}
