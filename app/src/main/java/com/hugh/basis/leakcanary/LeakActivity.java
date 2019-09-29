package com.hugh.basis.leakcanary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hugh.basis.HughApplication;
import com.hugh.basis.R;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by chenyw on 2019-09-05.
 */
public class LeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_test);
        LeakThread leakThread = new LeakThread();
        leakThread.start();
    }

    class LeakThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(6 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在这个例子中onDestroy方法是多余的，
        // 因为LeakCanary在调用install方法时会启动一个ActivityRefWatcher类，
        // 它用于自动监控Activity执行onDestroy方法之后是否发生内存泄露。这里只是为了方便举例，如果想要监控Fragment，在Fragment中添加如上的onDestroy方法是有用的。
        RefWatcher refWatcher = HughApplication.getRefWatcher(this);//1
        refWatcher.watch(this);
    }
}
