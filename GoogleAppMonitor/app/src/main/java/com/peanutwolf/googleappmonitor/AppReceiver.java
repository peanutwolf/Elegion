package com.peanutwolf.googleappmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class AppReceiver extends BroadcastReceiver {

    public static final String TAG = AppReceiver.class.getSimpleName();

    public static final String packageName = "PACKAGE_NAME";

    public AppReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.PACKAGE_ADDED")){
            Uri data = intent.getData();

            Toast.makeText(context, "Received application", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Received " + packageName + " = " + data);

            Intent mainActivity = new Intent(context.getApplicationContext(), MainActivity.class);
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mainActivity.putExtra(packageName, data.getSchemeSpecificPart());
            context.startActivity(mainActivity);
        }

    }
}
