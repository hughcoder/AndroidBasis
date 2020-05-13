package com.hugh.basis.thread;

import android.os.Bundle;
import android.os.Debug;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hugh.basis.R;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2019-11-15.
 */
public class ThreadTestAcitivity extends AppCompatActivity {

    private Button mCreateThread;
    private Button mGetThread;
    private Button mOpenTrace;
    private Button mCloseTrace;
    private Map<Thread, StackTraceElement[]> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);


        mCreateThread = findViewById(R.id.tv_create_thread);
        mCreateThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                             Log.e("bbb", "dosomething+");
                        }
                    });
                    thread.start();
                }
            }
        });

        mGetThread = findViewById(R.id.tv_get_thread);
        mGetThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map = Thread.getAllStackTraces();
                for (Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
                    Log.e("aaa","Key = " + entry.getKey().getName());
                    for(int j=0;j<entry.getValue().length;j++){
                        Log.e("aaa","Value = " + entry.getValue()[j].getClassName());
                        Log.e("aaa","Value = " + entry.getValue()[j].getFileName());
                    }
                }
            }
        });

        mOpenTrace = findViewById(R.id.btn_trace_open);
        mCloseTrace = findViewById(R.id.btn_trace_close);

        mOpenTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.startMethodTracing("trace");
            }
        });
        mCloseTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.stopMethodTracing();
            }
        });

        findViewById(R.id.tv_test_systrace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trace.beginSection("aaa");
                onLongTimeFun();
                Trace.endSection();
            }
        });
    }

    private synchronized void onLongTimeFun(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("aaa","开始前");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
