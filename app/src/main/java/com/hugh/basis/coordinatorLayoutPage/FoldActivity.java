package com.hugh.basis.coordinatorLayoutPage;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.hugh.basis.R;
import com.hugh.basis.coordinatorLayoutPage.bean.HomeTabEntity;
import com.hugh.basis.coordinatorLayoutPage.listpage.FoldListFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by chenyw on 2020/8/19.
 */
public class FoldActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private MagicIndicator mMagicIndicator;
    private CommonNavigator mCommonNavigator;
    private ArrayList<HomeTabEntity> mTabNames = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private HomeMainAdapter mFragmentadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_main);
        mViewPager = findViewById(R.id.viewPager);
        mMagicIndicator = findViewById(R.id.magic_indicator1);
        mFragments.clear();
        mTabNames.clear();
        mCommonNavigator = new CommonNavigator(this);
        mCommonNavigator.setScrollPivotX(0.65f);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTabNames == null ? 0 : mTabNames.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mTabNames.get(index).title);
                simplePagerTitleView.setNormalColor(Color.parseColor("#828282"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#1B1B1B"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }


        });
        mCommonNavigator.setSkimOver(true);
        mFragmentadapter = new HomeMainAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentadapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
        addData();
    }

    private void addData() {
        mTabNames.add(new HomeTabEntity("标题1"));
        mTabNames.add(new HomeTabEntity("标题2333"));
        FoldListFragment listFragment = new FoldListFragment();
        FoldListFragment list2Fragment = new FoldListFragment();
        mFragments.add(listFragment);
        mFragments.add(list2Fragment);
        mCommonNavigator.notifyDataSetChanged();
        mFragmentadapter.notifyDataSetChanged();
    }

    private class HomeMainAdapter extends FragmentPagerAdapter {

        public HomeMainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames.get(position).title;
        }

    }
}
