package com.hugh.basis.coordinatorLayoutPage.listpage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hugh.basis.R;
import com.hugh.basis.coordinatorLayoutPage.bean.SinglePicEntity;
import com.hugh.basis.coordinatorLayoutPage.vh.SinglePicVH;
import com.zhl.commonadapter.BaseViewHolder;
import com.zhl.commonadapter.CommonRecyclerAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chenyw on 2020/8/19.
 */
public class FoldListFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayout mLayoutTab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_fold_list, null);
        recyclerView = view.findViewById(R.id.rv_list);
        mLayoutTab = view.findViewById(R.id.layout_tab);

        ArrayList<SinglePicEntity> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new SinglePicEntity());
        }

        CommonRecyclerAdapter<SinglePicEntity> adapter = new CommonRecyclerAdapter<SinglePicEntity>(list) {
            @Override
            public BaseViewHolder<SinglePicEntity> createViewHolder(int type) {
                return new SinglePicVH();
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //控制上方tab栏隐藏显示

                Log.e("aaa", "dx:" + dx + "---dy:" + dy);

                //上滑隐藏
                if (dy > 1) {
                    if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_SETTLING && mLayoutTab.getVisibility() == View.VISIBLE) {
                        mLayoutTab.setVisibility(View.GONE);
                    }
                    //下滑显示
                } else if (dy < -1) {
                    if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_SETTLING && mLayoutTab.getVisibility() == View.GONE) {
                        mLayoutTab.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        return view;
    }
}
