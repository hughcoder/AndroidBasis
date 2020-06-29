package com.hugh.basis;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Debug;
import android.util.Log;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.hugh.basis.third.greendao.DaoMaster;
import com.hugh.basis.third.greendao.DaoSession;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.TinkerApplication;

import org.greenrobot.greendao.database.Database;

/**
 * Created by {chenyouwei}
 * Date: {2019/4/9}
 */
public class HughApplication extends Application {

    private static final String TAG = HughApplication.class.getSimpleName();
    private RefWatcher refWatcher;
    private DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
//        DoraemonKit.install(this);
//        LeakCanary.install(this);
        refWatcher= setupLeakCanary();
        Log.e(TAG, "onCreate : getProcessName:" );
        Log.e(TAG,"isDebug"+isApkInDebug(this));
        initDataBase();
    }

    private void initDataBase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
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
