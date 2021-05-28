package com.hugh.basis.scrolltest.scroll3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.hugh.basis.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ScrollTest3Activity extends Activity {
    private NestedRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_scroll_page3);
        mRecyclerView =  findViewById(R.id.rv_mus_list);
        //List布局
        CustomLayoutManager layoutManager = new CustomLayoutManager(this,mRecyclerView);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new CommonAdapter(this));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //设置最后一个item：tab+viewPager
                mRecyclerView.setLastItemView(ScrollManager.getInstance().getLastItemView());
            }
        });
        Log.e("aaa","ccc");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScrollManager.getInstance().removeList();
    }

    public static class SingleTextAdapter extends RecyclerView.Adapter<SingleTextAdapter.NoteViewHolder> {

        private List<String> datas;

        class NoteViewHolder extends RecyclerView.ViewHolder {

            public TextView text;

            public NoteViewHolder(View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.id_name);

            }
        }

        public void setData(@NonNull List<String> notes) {
            datas = notes;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_single_text, parent, false);
            return new NoteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NoteViewHolder holder, int position) {
            holder.text.setText(datas.get(position));
            final int num = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("aaa", "点击----->" + num);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }
}