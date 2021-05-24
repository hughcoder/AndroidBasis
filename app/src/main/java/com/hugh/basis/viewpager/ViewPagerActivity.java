package com.hugh.basis.viewpager;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hugh.basis.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by chenyw on 2019-07-15.
 */
public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private View view1, view2, view3;//需要滑动的页卡
    private List<View> views = new ArrayList<>();// Tab页面列表

    int[] imgRes = {R.drawable.image1, R.drawable.image2, R.drawable.image3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        mViewPager = findViewById(R.id.id_viewPager);
        // 设置Page的间距
        mViewPager.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(3);
        mAdapter = new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView view = new ImageView(ViewPagerActivity.this);
                view.setImageResource(imgRes[position]);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return imgRes.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }


        };
//        mViewPager.setAdapter(mAdapter);
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.activity_viewpager_lay1, null);
        view2 = inflater.inflate(R.layout.activity_viewpager_lay2, null);
        view3 = inflater.inflate(R.layout.activity_viewpager_lay3, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        mViewPager.setAdapter(new MyViewPagerAdapter(views));

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }


    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public void pageTransform(View view, float position) {
        if (position < -1) {
            view.setAlpha(mMinAlpha);
        } else if (position <= 1) { // [-1,1]

            if (position < 0) //[0，-1]
            {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else//[1，0]
            {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else { // (1,+Infinity]
            view.setAlpha(mMinAlpha);
        }
    }

}
