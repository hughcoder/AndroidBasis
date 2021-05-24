package com.hugh.basis.scrolltest.test2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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
        Log.e("aaa","setBottomContentView");
        mContentView = contentView;
    }

    public void setRvLinkView(RecyclerView recyclerView){
        Log.e("aaa","setBottomView");
        mLinkRvView = recyclerView;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        Log.e("aaa","onNestedPreFling---->y"+velocityY);
        return false;
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.e("aaa","onNestedFling---->y"+velocityY);
        return false;
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
