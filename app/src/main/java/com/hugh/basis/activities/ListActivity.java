package com.hugh.basis.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
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
 * NestedScrollView 包含rv,rv的scrollby失效
 */

public class ListActivity  extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mdatas;
    private HomeAdapter mAdapter;
    public boolean mScrollToMove;
    public int mScrollToPosition;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Log.e("aaa","bbb");

        initData();
        mRecyclerView = findViewById(R.id.id_recyclerview);
        scrollView = findViewById(R.id.scrollView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
//                StaggeredGridLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
        // 添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToPosition(8,mRecyclerView,false);
            }
        });
//        scrollToPosition(5,mRecyclerView,false);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                scrollToPosition(5,mRecyclerView,false);
            }
        });
    }

    /**
     * RecyclerView 移动到指定位置
     *
     * @param position     - 指定位置
     * @param recyclerView
     * @param smooth       - 是否光滑移动
     */
    public void scrollToPosition(int position, RecyclerView recyclerView, boolean smooth) {
        try {
            int firstItem = 0;
            int lastItem = 0;
            mScrollToPosition = position;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastItem = linearLayoutManager.findLastVisibleItemPosition();
            } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                firstItem = layoutManager.findFirstVisibleItemPosition();
                lastItem = layoutManager.findLastVisibleItemPosition();
            }

            Log.e("aaa firstItem",firstItem+"");
            Log.e("aaa lastItem",lastItem+"");
            if (position <= firstItem) {
                if (smooth) {
                    recyclerView.smoothScrollToPosition(position);
                } else {
                    recyclerView.scrollToPosition(position);
                }
            } else if (position <= lastItem) {
                int top = recyclerView.getChildAt(position - firstItem).getTop();
                if (smooth) {
                    recyclerView.smoothScrollBy(0, top, new LinearInterpolator(){
                        @Override
                        public float getInterpolation(float input) {
                            return super.getInterpolation(input);
                        }
                    });
                } else {
                    recyclerView.scrollBy(0, top);
//                    scrollView.scrollBy(0,top);
                }
            } else {
                mScrollToMove = true;
                if (smooth) {
                    recyclerView.smoothScrollToPosition(position);
                } else {
                    recyclerView.scrollToPosition(position);
                }
            }
        } catch (Exception e) {

        }
    }

    private void initData(){
        mdatas = new ArrayList<String>();
        for(int i='A';i<'z';i++){
            mdatas.add(""+(char)i);
        }
        Log.e("aaa", String.valueOf(mdatas.size()));
    }

    /**
     * RecyclerView 移动到指定位置
     * appbar 调试
     */
//    public void scrollToPosition(int position, RecyclerView recyclerView) {
//        try {
//            int firstItem = 0;
//            int lastItem = 0;
//            mScrollToPosition = position;
//            final CoordinatorLayout.Behavior behavior1 = ((CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams()).getBehavior();
//            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                firstItem = linearLayoutManager.findFirstVisibleItemPosition();
//                lastItem = linearLayoutManager.findLastVisibleItemPosition();
//            }
//            FZLogger.e("aaa", "firstItem" + firstItem);
//            FZLogger.e("aaa", "lastItem" + lastItem);
//            final int studyLayoutWidhMargin = (int)FZUtils.dp2px(mActivity,50);
//            if (position <= firstItem) {
//                final int top = recyclerView.getChildAt(position).getTop();
//                FZLogger.e("aaa<=firstItem", top + "");
//                mAppBarLayout.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int width = mAppBarLayout.getMeasuredWidth();
//                        int height = mAppBarLayout.getMeasuredHeight();
//                        if (behavior1 != null) {
//                            behavior1.onNestedPreScroll(coordinatorLayout, mAppBarLayout, mLayout, 0, top + height, new int[]{0, 0}, TYPE_NON_TOUCH);
//                            nestedScrollView.scrollTo(0, top+studyLayoutWidhMargin);
//                        }
//                    }
//                });
//            } else if (position <= lastItem) {
//                final int top = recyclerView.getChildAt(position - firstItem).getTop();// position 到firsItem之间的间距
//                FZLogger.e("aaa<=lastItem", top + "");
//                mAppBarLayout.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int width = mAppBarLayout.getMeasuredWidth();
//                        int height = mAppBarLayout.getMeasuredHeight();
//                        FZLogger.e("aaa", height + "");
//                        if (behavior1 != null) {
//                            behavior1.onNestedPreScroll(coordinatorLayout, mAppBarLayout, mLayout, 0, height, new int[]{0, 0}, TYPE_NON_TOUCH);
//                            nestedScrollView.scrollTo(0, top+studyLayoutWidhMargin);
//                        }
//
//                    }
//                });
//            } else {
//                final int top = recyclerView.getChildAt(position - lastItem).getTop();
//                nestedScrollView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        int width = mAppBarLayout.getMeasuredWidth();
//                        int height = mAppBarLayout.getMeasuredHeight();
//                        FZLogger.e("aaa", height + "");
//                        if (behavior1 != null) {
//                            behavior1.onNestedPreScroll(coordinatorLayout, mAppBarLayout, mLayout, 0, height, new int[]{0, 0}, TYPE_NON_TOUCH);
//                            nestedScrollView.scrollTo(0, top+studyLayoutWidhMargin);
//                        }
//
//                    }
//                });
//            }
//        } catch (Exception e) {
//
//        }
//    }

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
