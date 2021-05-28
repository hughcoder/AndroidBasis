package com.hugh.basis.scrolltest.scroll3;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hugh.basis.R;
import com.hugh.basis.scrolltest.test2.ScrollTest2Activity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    //模拟数据
    public String[] texts = {"java"};
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 1;//底部View个数
    List<String> stringList = new ArrayList<>();


    public CommonAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        for (int i = 0; i < 20; i++) {
            stringList.add("数据--->" + i);
        }
    }

    //内容长度
    public int getContentItemCount() {
        return texts.length;
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }


    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private NestedRecyclerView rv_list;

        public ContentViewHolder(View itemView) {
            super(itemView);
            rv_list = (NestedRecyclerView) itemView.findViewById(R.id.rv_item_list);
            ScrollManager.getInstance().addSingleView(rv_list);
            ScrollManager.getInstance().setLastItemView(itemView);
        }
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {

        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.item_header, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_list_content2, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(mLayoutInflater.inflate(R.layout.item_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("aaa", "header------------>");
                }
            });
        } else if (holder instanceof ContentViewHolder) {
            CustomLayoutManager layoutManager = new CustomLayoutManager(mContext, ((ContentViewHolder) holder).rv_list);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            ((ContentViewHolder) holder).rv_list.setLayoutManager(layoutManager);
            ScrollTest3Activity.SingleTextAdapter adapter = new ScrollTest3Activity.SingleTextAdapter();
            adapter.setData(stringList);
            ((ContentViewHolder) holder).rv_list.setAdapter(adapter);

        } else if (holder instanceof BottomViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

}
