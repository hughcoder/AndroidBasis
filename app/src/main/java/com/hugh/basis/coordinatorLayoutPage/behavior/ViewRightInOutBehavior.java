package com.hugh.basis.coordinatorLayoutPage.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;



import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 * Created by chenyw on 2020/9/2.
 */
public class ViewRightInOutBehavior extends CoordinatorLayout.Behavior<View> {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private boolean mAnimatingOut = false;


    public ViewRightInOutBehavior() {
    }

    public ViewRightInOutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //需要垂直的滑动
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (dyConsumed > 0 && !mAnimatingOut) {
            //向上滑动
            animateOut(child);
        } else if (dyConsumed < 0) {
            //向下滑动
            animateIn(child);
        }
    }

    private void animateOut(final View button) {
        ViewCompat.animate(button)
                .translationX(button.getWidth() + getMarginBottom(button))
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        mAnimatingOut = true;
                    }

                    public void onAnimationCancel(View view) {
                        mAnimatingOut = false;
                    }

                    public void onAnimationEnd(View view) {
                        mAnimatingOut = false;
                    }
                })
                .start();
    }

    private void animateIn(View button) {
        ViewCompat.animate(button).translationX(0).setInterpolator(INTERPOLATOR).withLayer().setListener(null).start();
    }

    private int getMarginBottom(View v) {
        final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return 0;
    }

}
