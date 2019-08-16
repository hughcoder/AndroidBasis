package com.hugh.basis.footer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hugh.basis.R;

/**
 * Created by chenyw on 2019-08-16.
 */
public class HeaderAndFooterActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CommonAdapter adapter;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_list);
        //List布局
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter=new CommonAdapter(this));
    }
}
