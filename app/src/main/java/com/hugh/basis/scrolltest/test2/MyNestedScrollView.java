package com.hugh.basis.scrolltest.test2;

import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import com.hugh.basis.common.utils.ScreenUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

//父 Scroll 发生滚动时，超出最大滚动距离的 contentOffset 交给其中一个子 Scroll 消耗，同时把自己滚动偏移设成最大滚动距离

public class MyNestedScrollView extends NestedScrollView {
    private int mTopViewHeight = ScreenUtil.dip2px(getContext(), 200);
    private View mContentView;
    private int mTopTitleHeight = ScreenUtil.dip2px(getContext(), 50);
    private RecyclerView mLinkRvView;
    private static final float INFLEXION = 0.35f;
    private float mFlingFriction = ViewConfiguration.getScrollFriction();
    final float ppi = getContext().getResources().getDisplayMetrics().density * 160.0f;
    private float mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                    * 39.37f // inch/meter
                            * ppi
                    * 0.84f;
    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));


    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBottomContentView(View contentView){
        Log.e("ccc","1");
        mContentView = contentView;
    }

    public void setRvLinkView(RecyclerView recyclerView){
        mLinkRvView = recyclerView;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        Log.e("ccc","onNestedPreFling---->y"+velocityY);
        return false;
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    private double getSplineFlingDistance(int velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return mFlingFriction * mPhysicalCoeff * Math.exp(DECELERATION_RATE / decelMinusOne * l);
    }

    private double getSplineDeceleration(int velocity) {
        return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff));
    }


    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.e("ccc","dispatchNestedFling vy2---->"+velocityY+"--consumed-->"+consumed);
        if(consumed&&mLinkRvView!=null&&velocityY>0){ //velocityY>0代表向下滑动
            Log.e("ccc","距离----->"+(int) getSplineFlingDistance((int) velocityY));
            mLinkRvView.smoothScrollBy(0, (int) getSplineFlingDistance((int) velocityY));
        }
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.e("ccc","onStartNestedScroll---axes-->"+axes+"-type----->"+type);
        return super.onStartNestedScroll(child, target, axes, type);
    }


    @Override
    public void onStopNestedScroll(@NonNull View target) {
        Log.e("ccc","onStopNestedScroll---->111");
        super.onStopNestedScroll(target);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        Log.e("ccc","onStopNestedScroll---->222");
        super.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.e("aaa", "onNestedPreScroll  dx--->" + dx + ",dy------>" + dy);
        boolean hideTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !target.canScrollVertically(-1);
        if (hideTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }


    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        super.scrollTo(x, y);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //当子控件处理完后，交给父控件进行处理。
        Log.e("aaa", "onNestedScroll  dyConsumed------>" + dyConsumed);
        if (dyUnconsumed < 0) {//表示已经向下滑动到头
            scrollBy(0, dyUnconsumed);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //mLayout修改后的高度= 总高度-导航栏高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mContentView!=null){
            Log.e("aaa","mLinkRvView设置高度");
            ViewGroup.LayoutParams layoutParams = mContentView.getLayoutParams();
            layoutParams.height = getMeasuredHeight() - mTopTitleHeight;
            mContentView.setLayoutParams(layoutParams);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

}
