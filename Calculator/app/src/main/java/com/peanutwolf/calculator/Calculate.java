package com.peanutwolf.calculator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class Calculate extends Service {

    private final IBinder mBinder = new LocalBinder();

    public Calculate() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public String calculate(Float arg1, Float arg2, Operations op){
        Float result = 0F;
        switch (op){
            case PLUS:
                result = arg1 + arg2;
                break;
            case MINUS:
                result = arg1 - arg2;
                break;
            case MULTIPLY:
                result = arg1 * arg2;
                break;
            case DIVIDE:
                if(arg2 == 0){
                    return getString(R.string.calc_err);
                }
                result = arg1 / arg2;
                break;
            default:
                return getString(R.string.unknown_op);
        }
        return result.toString();
    }

    public class LocalBinder extends Binder {
        Calculate getService() {
            return Calculate.this;
        }
    }
}
