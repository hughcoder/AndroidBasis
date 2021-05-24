package com.hugh.basis.scrolltest.adapter;

import android.view.View;
import android.widget.TextView;

import com.hugh.basis.R;
import com.zhl.commonadapter.BaseViewHolder;

public class TextAdapter extends BaseViewHolder<String> {

    private TextView mTvName;


    @Override
    public void findView(View view) {
        mTvName = view.findViewById(R.id.id_name);
    }

    @Override
    public void updateView(String data, int position) {
        mTvName.setText(data);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_single_text;
    }
}
