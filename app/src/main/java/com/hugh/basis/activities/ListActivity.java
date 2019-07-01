package com.hugh.basis.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugh.basis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyw on 2019-06-30.
 *
 * LinearLayoutManager 现行管理器，支持横向、纵向。
 * GridLayoutManager 网格布局管理器
 * StaggeredGridLayoutManager 瀑布就式布局管理器
 */

public class ListActivity  extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mdatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Log.e("aaa","bbb");

        initData();
        mRecyclerView = findViewById(R.id.id_recyclerview);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
        // 添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void initData(){
        mdatas = new ArrayList<String>();
        for(int i='A';i<'z';i++){
            mdatas.add(""+(char)i);
        }
        Log.e("aaa", String.valueOf(mdatas.size()));
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    ListActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;

        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if(holder.tv !=null){
                holder.tv.setText(mdatas.get(position));
            }else {
                Log.e("aaa","为null");
            }
        }


        @Override
        public int getItemCount() {
            return mdatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView tv;


            public MyViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.id_num);
            }
        }
    }
}
