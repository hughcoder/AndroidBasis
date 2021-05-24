package com.hugh.basis.scrolltest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hugh.basis.R;
import com.hugh.basis.common.utils.ScreenUtil;
import com.hugh.basis.scrolltest.adapter.TextAdapter;
import com.hugh.basis.scrolltest.test2.MyNestedScrollView;
import com.zhl.commonadapter.BaseViewHolder;
import com.zhl.commonadapter.CommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollTestActivity extends Activity {
    private List<String> mLeftDatas;
    private Map<String, List<String>> map;
    private List<String> mRightDatas;
    private RecyclerView mRvLeft;
    private RecyclerView mRvRight;
    private MyNestedScrollView nestedScrollView;
    private TextView mTvTextTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        initData();
        mRvLeft = findViewById(R.id.rv_left);
        mRvRight = findViewById(R.id.rv_right);
//        nestedScrollView = findViewById(R.id.nested_scrollview);
        mTvTextTop = findViewById(R.id.tv_top_view);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTvTextTop.getLayoutParams();
        lp.height = ScreenUtil.dip2px(this,200);
        mTvTextTop.setLayoutParams(lp);
        initRv();
    }

    private void initData() {
        mLeftDatas = new ArrayList<String>();
        mLeftDatas.add("韩愈");
        mLeftDatas.add("柳宗元");
        mLeftDatas.add("苏轼");
        mLeftDatas.add("苏辙");
        mLeftDatas.add("苏洵");
        mLeftDatas.add("欧阳修");
        mLeftDatas.add("曾巩");
        mLeftDatas.add("王安石");
        for (int i = 0; i < 10; i++) {
            mLeftDatas.add("左边的数字哈哈-->" + i);
        }

        mRightDatas = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            mRightDatas.add("数字-->" + i);
        }
    }

    private void initRv() {
        CommonRecyclerAdapter<String> adapterLeft = new CommonRecyclerAdapter<String>(mLeftDatas) {
            @Override
            public BaseViewHolder<String> createViewHolder(int type) {
                return new TextAdapter();
            }
        };
        mRvLeft.setAdapter(adapterLeft);
        mRvLeft.setLayoutManager(new LinearLayoutManager(this));

        CommonRecyclerAdapter<String> rightAdapter = new CommonRecyclerAdapter<String>(mRightDatas) {
            @Override
            public BaseViewHolder<String> createViewHolder(int type) {
                return new TextAdapter();
            }
        };
        mRvRight.setAdapter(rightAdapter);
        mRvRight.setLayoutManager(new LinearLayoutManager(this));
    }



}
