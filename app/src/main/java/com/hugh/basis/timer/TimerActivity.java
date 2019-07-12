package com.hugh.basis.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hugh.basis.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chenyw on 2019-07-12.
 */
public class TimerActivity extends AppCompatActivity {

    private weakHandler handler;
    private Timer timer;
    private TimerTask task;
    private Button button;
    private Button handlerbutton;
    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        button = findViewById(R.id.btn_delete_time);
        handlerbutton = findViewById(R.id.handler_start);

        timer = new Timer();
        handler = new weakHandler(this, timer);
        task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = 10;
                handler.sendMessage(message);
            }
        };

        Message message = new Message();
        message.what = 10;
        Log.e("aaa", "开启定时");
        handler.sendMessageDelayed(message, 6 * 1000);
        //启动定时器
//        timer.schedule(task, 60 * 1000, 10 * 60 * 1000);
//        timer.schedule(task, 5 * 1000, 10 * 60 * 1000);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaa", "删除定时");
                handler.removeMessages(10);
            }
        });

        handlerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaa", "handler开启定时");
                Message message = new Message();
                message.what = 10;
                handler.sendMessageDelayed(message, 6 * 1000);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private static class weakHandler extends Handler {
        //WeakReference 弱引用
        private final WeakReference<TimerActivity> mActivity;
        private final Timer mtimer;

        public weakHandler(TimerActivity activity, Timer timer) {
            mActivity = new WeakReference<TimerActivity>(activity);
            mtimer = timer;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 10) {
                Log.e("aaa", "处理消息");
            }
        }
    }
}
