package com.hugh.basis;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Debug;
import android.util.Log;

import com.didichuxing.doraemonkit.DoraemonKit;

/**
 * Created by {chenyouwei}
 * Date: {2019/4/9}
 */
public class HughApplication extends Application {

    private static final String TAG = HughApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        DoraemonKit.install(this);

        Log.e(TAG, "onCreate : getProcessName:" );
        Log.e(TAG,"isDebug"+isApkInDebug(this));
    }

    //判断当前应用是否是debug状态
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
