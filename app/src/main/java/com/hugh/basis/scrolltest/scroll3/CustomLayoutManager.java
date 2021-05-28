package com.hugh.basis.scrolltest.scroll3;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CustomLayoutManager extends LinearLayoutManager {

    private NestedRecyclerView musRecyclerView;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public CustomLayoutManager(Context context, NestedRecyclerView musRecyclerView) {
        super(context);
        this.musRecyclerView = musRecyclerView;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            int scroll = super.scrollVerticallyBy(dy, recycler, state);
            if (musRecyclerView != null) {
                if (scroll == 0) {
                    if (dy > 0) {
                        musRecyclerView.setReachBottomEdge(true);
                    } else if (dy < 0) {
                        musRecyclerView.setReachTopEdge(true);
                    }
                } else if (dy != 0) {
                    musRecyclerView.setReachBottomEdge(false);
                    musRecyclerView.setReachTopEdge(false);
                }
            }
            return scroll;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

}
