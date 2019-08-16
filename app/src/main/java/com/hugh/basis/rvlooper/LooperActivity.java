package com.hugh.basis.rvlooper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.hugh.basis.R;
import com.hugh.basis.rvlooper.bean.GroupBookingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyw on 2019-08-15.
 */
public class LooperActivity extends AppCompatActivity {

    private AutoPollRecyclerView mRecyclerView;
    private List<GroupBookingEntity> list = new ArrayList<>();
    private AutoPollAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper_rv);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            GroupBookingEntity entity = new GroupBookingEntity();
            entity.nickname = "我是一个游客"+i;
            entity.collage_people = "10";
            entity.now_people = i+"";
            entity.end_time = "86400";
            entity.current_time =(100+i*30)+"";
            entity.id="id"+i;
            list.add(entity);
        }
    }

    private void initView() {
        mRecyclerView = (AutoPollRecyclerView) findViewById(R.id.rv_recycleView);
        mAdapter = new AutoPollAdapter(list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mRecyclerView) {
            mRecyclerView.stop();
        }
        if(mAdapter!=null){
            mAdapter.onDestroy();
        }
    }
}
