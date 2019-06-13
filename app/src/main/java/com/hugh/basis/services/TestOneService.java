package com.hugh.basis.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by chenyw on 2019-06-13.
 */
public class TestOneService extends Service {

    @Override
    public void onCreate() {
        Log.e("Kathy","onCreate - Thread ID = " + Thread.currentThread().getId());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Kathy", "onStartCommand - startId = " + startId + ", Thread ID = " + Thread.currentThread().getId());
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Kathy", "onBind - Thread ID = " + Thread.currentThread().getId());
        return null;
    }

    @Override
    public void onDestroy() {
        Log.e("Kathy", "onDestroy - Thread ID = " + Thread.currentThread().getId());
        super.onDestroy();
    }
}
