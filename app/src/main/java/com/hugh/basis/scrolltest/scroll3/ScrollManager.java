package com.hugh.basis.scrolltest.scroll3;

import android.view.View;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 主要处理父scroll的fling传递给对应的子Scroll
public class ScrollManager {

    private static ScrollManager instance = null;
    private List<View> mChildScrollList = new ArrayList<>(); //子滑动存scroll or rv
    private HashMap<NestedRecyclerView, Boolean> hashMap = new HashMap<>(); // 记录当前节点的rv是否触底  更新状态

    //rv下滑的时候要记录多个rv
    private List<NestedRecyclerView> mRvNestedRvList = new ArrayList<>(); //需要记录每一个子View还有没有滑动能力
    private View mLastItemView;

    public static ScrollManager getInstance() {
        // 先判断实例是否存在，若不存在再对类对象进行加锁处理
        if (instance == null) {
            synchronized (ScrollManager.class) {
                if (instance == null) {
                    instance = new ScrollManager();
                }
            }
        }
        return instance;
    }

    public View getLastItemView() {
        return mLastItemView;
    }

    public void setLastItemView(View mLastItemView) {
        this.mLastItemView = mLastItemView;
    }

    public void addSingleView(View view) {
        mChildScrollList.add(view);
    }

    public List<View> getViewLists() {
        return mChildScrollList;
    }

    public void removeList() {
        mChildScrollList.clear();
    }


    public void addRvView(NestedRecyclerView view) {
        mRvNestedRvList.add(view);
    }

    public List<NestedRecyclerView> getRvLists() {
        return mRvNestedRvList;
    }

    public boolean isContainRv(NestedRecyclerView curRv) {
        for (int i = 0; i < mRvNestedRvList.size(); i++) {
            if (curRv == mRvNestedRvList.get(i)) {
                //如果有的话
                return true;
            }
        }
        return false;
    }

    public int getCurRvIndex(NestedRecyclerView rv) {
        for (int i = 0; i < mRvNestedRvList.size(); i++) {
            if (rv == mRvNestedRvList.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public void removeRvList() {
        mRvNestedRvList.clear();
    }


    public void updateCurRvStatus(NestedRecyclerView rv, boolean mIsReachBottom) {
        //更新是否到底部
        hashMap.put(rv, mIsReachBottom);

    }

    public boolean getCurRvStatus(NestedRecyclerView rv) {
        return hashMap.get(rv);
    }

}
