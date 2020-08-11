package com.hugh.basis.home.vh;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.basis.R;
import com.hugh.basis.home.model.HomeTabEntity;
import com.zhl.commonadapter.BaseViewHolder;

/**
 * Created by chenyw on 2020/8/11.
 */
public class SingleHomeTabVH extends BaseViewHolder<HomeTabEntity> {

    private ImageView mIvTab;
    private TextView mTvTitle;

    @Override
    public void findView(View view) {
        mIvTab = view.findViewById(R.id.iv_tab);
        mTvTitle = view.findViewById(R.id.tv_title);
    }

    @Override
    public void updateView(HomeTabEntity data, int position) {
        mTvTitle.setText(data.getTitle());
        int index = position % 6;
        switch (index) {
            case 1:
                mIvTab.setBackgroundResource(R.drawable.ic_tab_place);
                break;
            case 2:
                mIvTab.setBackgroundResource(R.drawable.ic_tab_img1);
                break;
            case 3:
                mIvTab.setBackgroundResource(R.drawable.ic_tab_img2);
                break;
            case 4:
                mIvTab.setBackgroundResource(R.drawable.ic_tab_img3);
                break;
            case 5:
                mIvTab.setBackgroundResource(R.drawable.ic_tab_img4);
                break;
            case 0:
                mIvTab.setBackgroundResource(R.drawable.ic_tab_img5);
                break;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.module_home_item_singletab;
    }
}
