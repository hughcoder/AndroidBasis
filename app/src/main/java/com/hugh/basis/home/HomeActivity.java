package com.hugh.basis.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hugh.basis.MainActivity;
import com.hugh.basis.R;
import com.hugh.basis.coordinatorLayoutPage.FoldActivity;
import com.hugh.basis.home.homeSub.HomeSubActivity;
import com.hugh.basis.home.model.HomeTabEntity;
import com.hugh.basis.home.vh.SingleHomeTabVH;
import com.zhl.commonadapter.BaseViewHolder;
import com.zhl.commonadapter.CommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chenyw on 2020/8/11.
 */
public class HomeActivity extends Activity {

    private TextView mTvTitle;
    private RecyclerView mRvList;
    private List<HomeTabEntity> mHomeTabEntityList;
    private CommonRecyclerAdapter<HomeTabEntity> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRvList = findViewById(R.id.rv_list);
        mTvTitle = findViewById(R.id.tv_title);
        mTvTitle.setText("主页面");
        iniData();
        mAdapter = new CommonRecyclerAdapter<HomeTabEntity>(mHomeTabEntityList) {
            @Override
            public BaseViewHolder<HomeTabEntity> createViewHolder(int type) {
                return new SingleHomeTabVH();
            }
        };
        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int type = mHomeTabEntityList.get(position).getType();
                if (type == 5) {
                    HomeActivity.this.startActivity(new Intent(HomeActivity.this, MainActivity.class));
                } else {
                    HomeActivity.this.startActivity(HomeSubActivity.createIntent(HomeActivity.this, mHomeTabEntityList.get(position).getType()));
                }
            }
        });
        mRvList.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeActivity.this, 3);
        mRvList.setLayoutManager(gridLayoutManager);
    }

    private void goTest(){
        startActivity(new Intent(HomeActivity.this, FoldActivity.class));
    }

    private void iniData() {
        mHomeTabEntityList = new ArrayList<>();
        HomeTabEntity tab1 = new HomeTabEntity("img1", "AndroidUI", 1);
        HomeTabEntity tab2 = new HomeTabEntity("img1", "Android第三方库测试", 2);
        HomeTabEntity tab4 = new HomeTabEntity("img1", "音视频相关", 3);
        HomeTabEntity tab5 = new HomeTabEntity("img1", "Android功能测试", 4);
        HomeTabEntity tab6 = new HomeTabEntity("img1", "找不到东西来这汇总页面", 5);
        mHomeTabEntityList.add(tab1);
        mHomeTabEntityList.add(tab2);
        mHomeTabEntityList.add(tab4);
        mHomeTabEntityList.add(tab5);
        mHomeTabEntityList.add(tab6);
    }
}
