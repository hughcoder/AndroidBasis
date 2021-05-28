package com.hugh.basis.scrolltest.scroll3;

import android.content.Context;
import android.hardware.SensorManager;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;


public class NestedRecyclerView extends RecyclerView implements NestedScrollingParent2 {

    private static final float INFLEXION = 0.35f;
    private float mFlingFriction = ViewConfiguration.getScrollFriction();
    final float ppi = getContext().getResources().getDisplayMetrics().density * 160.0f;
    private float mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
            * 39.37f // inch/meter
            * ppi
            * 0.84f;
    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));

    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private boolean mIsReachTop = true;//默认顶上
    private boolean mIsReachBottom = false;//初始值有点问题
    private View mLastItemView; //这边不考虑footer就可以拿最底层item 滑动到距离顶上的最小距离即为外层可滑动的距离
    private int mLastItemMax = -100;//记录最大值
    private int mLastItemMin = -100;//记录最小值

    public void setReachBottomEdge(boolean reachBottomEdge) {
        mIsReachBottom = reachBottomEdge;
    }

    public void setReachTopEdge(boolean reachTopEdge) {
        mIsReachTop = reachTopEdge;
    }

    public boolean getReachBottomEdge() {
        //判断是否到达底部
        return mIsReachBottom;
    }

    public void setLastItemView(View view) {
        mLastItemView = view;
    }

    public NestedRecyclerView(Context context) {
        this(context, null);
    }

    public NestedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }


    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e("aaa","onNestedPreFling--->"+velocityY+",target--->"+target);
        return false;
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.e("aaa","onNestedFling--->"+velocityY+",target--->"+target);
        return false;
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        //向下分发 filing
        Log.e("ccc", "dispatchNestedFling---->" + velocityY);
        if (ScrollManager.getInstance().getViewLists().size() > 0) {
            View curRv = ScrollManager.getInstance().getViewLists().get(0);
            if (curRv instanceof RecyclerView && velocityY > 0) { //先只处理大于0的状况
                //velocityY>0代表向下滑动
                Log.e("aaa", "fling距离1---->" + (int) getSplineFlingDistance((int) velocityY));
                //下面注释还是会出现那个问题
                ((RecyclerView) curRv).smoothScrollBy(0, (int) getSplineFlingDistance((int) velocityY)/2);
            }
        }
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
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
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.e("ccc", "onStartNestedScroll---->type" + type + "");
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mNestedScrollingParentHelper.onStopNestedScroll(target, type);
    }

    //记录子容器到顶部的最小距离 即到顶
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
//        dispatchNestedPreScroll(dx, dy, consumed, null);
        Log.e("ccc", "onNestedPreScroll  dy滑动距离------>" + dy);
        Log.e("ccc", "mIsReachTop1--->" + mIsReachTop + ",mIsReachBottom1--->" + mIsReachBottom);
        //子控件未滑动之前,由父元素先处理
        if (dy > 0) {
            //向上滑如果还没触底
            if (!mIsReachBottom) {
                //向上滑 还没触底则自己消费
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        } else {
            //向下滑 自己优先处理如果自己还没触顶先消费
            if(!mIsReachBottom){
                //如果父View没在底部先滑动
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
    }

    //dyUnconsumed 垂直方向嵌套滑动的子控件未滑动的距离(未消耗的距离)
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //当子控件处理完后，交给父控件进行处理。 target 为子View rv_item_list
        Log.e("ccc", "onNestedScrollz  dyUnconsumed------>" + dyUnconsumed + ",dyConsumed----->" + dyConsumed);
        //从下往上滑
        if (dyUnconsumed < 0) {//表示子控件已经向下滑动到头
            scrollBy(0, dyUnconsumed);
        }
    }

    private boolean mIsHasList = false;

    public void traverseViewGroup(View view) {
        if (null == view) {
            return;
        }
        if (view instanceof ViewGroup) {
            //遍历ViewGroup,是子view加1，是ViewGroup递归调用
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View child = ((ViewGroup) view).getChildAt(i);
                if (child instanceof ViewGroup) {
                    if (child instanceof RecyclerView) {
                        mIsHasList = true;
                        break;
                    } else {
                        traverseViewGroup(child);
                    }
                }
            }
        }
    }


    private float mTouchDown;

    //
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //如果静止且按下
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("aaa", "ACTION_DOWN----->" + e.getY());
                //如果落点的位置没有rv或者scroll 就由自身处理 或者这么判断（如果父元素是实现嵌套的,那也由自己拦截）
                //如果落点是在子列表上,就传递下去
                mTouchDown = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //看下此时的View是否触顶或触底 以及滑动放下
//                Log.e("aaa", "值ACTION_MOVE----->" + e.getY());
                //先看下落指的点是否含有列表
                if (!ScrollManager.getInstance().isContainRv(this)) {
                    //如果没有接加进去 最外层到里面
                    ScrollManager.getInstance().addRvView(this); //记录列表
                }
                //更新每层View的滑动状态
                ScrollManager.getInstance().updateCurRvStatus(this, mIsReachTop); //看看有没有在顶部，没在顶部能往上滑
                View child = findChildViewUnder(e.getX(), e.getY());
                mIsHasList = false;
                traverseViewGroup(child);
                if (!mIsHasList) {
                    //遍历child 如果没有滑动的话就直接走正常流程
                    Log.e("aaa", "没有相关滚动View");
                    return super.onInterceptTouchEvent(e);
                }

                float num = e.getY() - mTouchDown;
                mTouchDown = e.getY();//接下来都是move了 要更新
//                Log.e("aaa", "差值zzz----->" + (int) num);
                //当前View触顶，然后还向上滑，其实已经滑不动了，所以本次不拦截
                //向上滑动的时候记录View的拦截顺序 num是小于0
//                Log.e("aaa", "mIsReachTop1--->" + mIsReachTop + ",mIsReachBottom1--->" + mIsReachBottom + ",当前View --->" + this.toString());
                if (num < 0) {
                    //num<0是往上推，往上的时候统一外层先处理,如果外层没有拉到底部 有外层消费，否则外层不消费
//                    Log.e("aaa", "往上往上 --->");
                    if (!mIsReachBottom) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (num > 0) {
//                    Log.e("aaa", "size----->" + ScrollManager.getInstance().getRvLists().size());
                    //这里先不管外面有没有到底，都是里面先滑
                    //当前View触底了还想下滑，这里需要子View先判断
                    if (ScrollManager.getInstance().getCurRvIndex(this) != -1) {
                        //如果当前节点为最内层
                        if (ScrollManager.getInstance().getCurRvIndex(this) == ScrollManager.getInstance().getRvLists().size() - 1) {
                            Log.e("aaa", "最内层节点---->" + this.toString());
                            return super.onInterceptTouchEvent(e);
                        }
                        for (int i = ScrollManager.getInstance().getCurRvIndex(this) + 1; i < ScrollManager.getInstance().getRvLists().size(); i++) {
                            NestedRecyclerView curRv = ScrollManager.getInstance().getRvLists().get(i);
                            //得到后续的节点
                            Log.e("aaa", "当前节点是否触顶" + ScrollManager.getInstance().getCurRvStatus(curRv) + "，当前View--->" + this.toString());
                            if (!ScrollManager.getInstance().getCurRvStatus(curRv)) {
                                //如果内部没有触顶 说明还能向上滑 先由内层接管
                                //如果内层也符合条件 //当前不拦截
                                return false;
                            }
                        }
                    }
                    //如果内层没有则自己消费
                    return super.onInterceptTouchEvent(e);

                }
            default:
                break;
        }

        return super.onInterceptTouchEvent(e);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        Log.e("aaa", "onTouchEvent--->+" + "当前View---->" + this.toString());
//        Log.e("aaa", "onTouchEvent--->" + "当前事件---->" + e.toString());
//        Log.e("aaa", "onTouchEvent--->" + "当前onTouchEvent返回值---->" + super.onTouchEvent(e));
//        return super.onTouchEvent(e);
//    }

    private boolean isParentSupportNestedScroll() {
        ViewParent p = getParent();
        while (p != null) {
            if (p instanceof NestedScrollView || p instanceof NestedRecyclerView) {
                return true;
            }
            p = p.getParent();
        }
        return false;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        Log.e("aaa","onScrolled ---dy---->"+dy);
        super.onScrolled(dx, dy);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }


}
