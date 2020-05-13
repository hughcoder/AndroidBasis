package com.hugh.basis.eventbus;

import android.os.Bundle;
import android.view.View;

import com.hugh.basis.R;
import com.hugh.basis.eventbus.message.AEvent;
import com.hugh.basis.eventbus.message.MessageEvent;
import com.hugh.basis.eventbus.message.SuccessEvent;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2019-07-05.
 */
public class EventActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event2);
        findViewById(R.id.sendMessageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("Hello !....."));
                EventBus.getDefault().post(new AEvent()); //包名不同不行的 可以看下类加载相关机制
                finish();
            }
        });

        findViewById(R.id.sendMessageBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SuccessEvent("我是登陆成功的消息 哈哈哈"));
                finish();
            }
        });
    }
}
