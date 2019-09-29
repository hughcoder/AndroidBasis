package com.hugh.basis;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Debug;
import android.util.Log;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by {chenyouwei}
 * Date: {2019/4/9}
 */
public class HughApplication extends Application {

    private static final String TAG = HughApplication.class.getSimpleName();
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
//        DoraemonKit.install(this);
//        LeakCanary.install(this);
        refWatcher= setupLeakCanary();
        Log.e(TAG, "onCreate : getProcessName:" );
        Log.e(TAG,"isDebug"+isApkInDebug(this));
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }


    public static RefWatcher getRefWatcher(Context context) {
        HughApplication application = (HughApplication) context.getApplicationContext();
        return application.refWatcher;
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
