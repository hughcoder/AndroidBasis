package com.hugh.basis.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hugh.basis.R;
import com.hugh.basis.eventbus.message.MessageEvent;

import org.greenrobot.eventbus.EventBus;

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
                finish();
            }
        });
    }
}
