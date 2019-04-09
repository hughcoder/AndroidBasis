package com.hugh.basis;

import android.app.Application;
import android.util.Log;

/**
 * Created by {chenyouwei}
 * Date: {2019/4/9}
 */
public class HughApplication extends Application {

    private static final String TAG = HughApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "onCreate : getProcessName:" );
    }
}
