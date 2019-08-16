package com.hugh.basis.rvlooper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.hugh.basis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyw on 2019-08-15.
 */
public class LooperActivity extends AppCompatActivity {

    private AutoPollRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper_rv);
        initView();
    }
    private void initView() {
        mRecyclerView = (AutoPollRecyclerView) findViewById(R.id.rv_recycleView);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; ) {
            list.add(" Item: " + ++i);
        }
        AutoPollAdapter adapter = new AutoPollAdapter(list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mRecyclerView){
            mRecyclerView.stop();
        }
    }
}
