package com.hugh.basis.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

/**
 * Created by chenyw on 2019-06-13.
 */
public class TestTwoService extends Service {

    //client 可以通过Binder获取Service实例
    public class MyBinder extends Binder{
        public TestTwoService getService(){
            return TestTwoService.this;
        }
    }

//通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();
    private final Random generator = new Random();

    @Override
    public void onCreate() {
        Log.i("Kathy","TestTwoService - onCreate - Thread = " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Kathy", "TestTwoService - onStartCommand - startId = " + startId + ", Thread = " + Thread.currentThread().getName());
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Kathy", "TestTwoService - onBind - Thread = " + Thread.currentThread().getName());
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("Kathy", "TestTwoService - onUnbind - from = " + intent.getStringExtra("from"));
        return false;
    }

    @Override
    public void onDestroy() {
        Log.e("Kathy", "TestTwoService - onDestroy - Thread = " + Thread.currentThread().getName());
        super.onDestroy();
    }

    //getRandomNumber是Service暴露出去供client调用的公共方法
    public int getRandomNumber() {
        return generator.nextInt();
    }
}
