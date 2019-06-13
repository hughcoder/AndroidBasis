package com.hugh.basis.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hugh.basis.R;
import com.hugh.basis.services.TestTwoService;

/**
 * Created by chenyw on 2019-06-13.
 * client端要做的事情：
 * 1.创建ServiceConnection类型实例，并重写onServiceConnected()方法和onServiceDisconnected()方法。
 * 2.当执行到onServiceConnected回调时，可通过IBinder实例得到Service实例对象，这样可实现client与Service的连接。
 * 3.onServiceDisconnected回调被执行时，表示client与Service断开连接，在此可以写一些断开连接后需要做的处理。
 */

public class BindServiceActivity extends AppCompatActivity implements Button.OnClickListener {

    private TestTwoService service = null;
    private boolean isBind = false;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            isBind = true;
            TestTwoService.MyBinder myBinder = (TestTwoService.MyBinder) binder;
            service = myBinder.getService();
            Log.e("Kathy", "ActivityA - onServiceConnected");
            int num = service.getRandomNumber();
            Log.e("Kathy", "ActivityA - getRandomNumber = " + num);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            Log.e("Kathy", "ActivityA - onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        findViewById(R.id.btnStartActivityB).setOnClickListener(this);
        findViewById(R.id.btnFinish).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e("aaa","点击");
        if (v.getId() == R.id.btnBindService) {
            //单击了“bindService”按钮
            Intent intent = new Intent(this, TestTwoService.class);
            intent.putExtra("from", "ActivityA");
            Log.e("Kathy", "----------------------------------------------------------------------");
            Log.e("Kathy", "ActivityA 执行 bindService");
            bindService(intent, conn, BIND_AUTO_CREATE);
        }else if (v.getId() == R.id.btnUnbindService) {
            //单击了“unbindService”按钮
            if (isBind) {
                Log.e("Kathy",
                        "----------------------------------------------------------------------");
                Log.e("Kathy", "ActivityA 执行 unbindService");
                unbindService(conn);
            }
        } else if (v.getId() == R.id.btnStartActivityB) {
            //单击了“start ActivityB”按钮
//            Intent intent = new Intent(this, ActivityB.class);
            Log.e("Kathy",
                    "----------------------------------------------------------------------");
            Log.e("Kathy", "ActivityA 启动 ActivityB");
//            startActivity(intent);
        } else if (v.getId() == R.id.btnFinish) {
            //单击了“Finish”按钮
            Log.e("Kathy",
                    "----------------------------------------------------------------------");
            Log.e("Kathy", "ActivityA 执行 finish");
            this.finish();
        }
    }
}
