package com.hugh.basis.animate;

import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.hugh.basis.R;

/**
 * Created by {chenyouwei}
 * Date: {2019/4/15}
 */
public class AnimationActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mIvImg;
    private Button mBtnTranslate;
    private Button mBtnFinal;
    private int mX;
    private int mY;
    private int mTranlateX;
    private int mTranslateY;
    private Button mBtnAlpha;
    private ImageView mIv1;
    private ImageView mIv2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);

        mButton = findViewById(R.id.btn_click);
        mBtnFinal = findViewById(R.id.btn_final);
        mBtnTranslate = findViewById(R.id.btn_translate);
        mBtnAlpha = findViewById(R.id.btn_alpha);
        mIvImg = findViewById(R.id.iv_img1);
        mIvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //缩放动化
                startScaleAnimation();
            }
        });

        mBtnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate(mBtnTranslate);
            }
        });

//        ObjectAnimator.ofFloat(mButton, "translationY", 800).start();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofInt(mButton, "width", 500).setDuration(5000).start();
            }
        });

        ViewTreeObserver observer = mBtnFinal.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBtnFinal.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mX =mBtnFinal.getLeft();
                mY = mBtnFinal.getTop();
            }
        });

        ViewTreeObserver observer2 = mBtnTranslate.getViewTreeObserver();
        observer2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBtnTranslate.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mTranlateX =mBtnTranslate.getLeft();
                mTranslateY = mBtnTranslate.getTop();
            }
        });
        mBtnAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alphaAnimation();
            }
        });

        mIv1 = findViewById(R.id.iv_img_4);
        mIv2 = findViewById(R.id.iv_move_3);
        ViewTreeObserver observer3 = mIv1.getViewTreeObserver();
        observer3.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBtnTranslate.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.e("aaa",mIv1.getWidth()+"getWidth---mIv1");
                Log.e("aaa",mIv1.getHeight()+"getHeight---mIv1");
            }
        });
        ViewTreeObserver observer4 = mIv2.getViewTreeObserver();
        observer4.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBtnTranslate.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.e("aaa",mIv2.getWidth()+"getWidth---mIv2");
                Log.e("aaa",mIv2.getHeight()+"getHeight---mIv2");
            }
        });
    }


    public void translate(View view) {
        Log.e("aaa","mX-------"+mX);
        Log.e("aaa","mY-------"+mY);

        Log.e("aaa","mTranlateX-------"+mTranlateX);
        Log.e("aaa","mTranslateY-------"+mTranslateY);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, mX-mTranlateX, 0, mY-mTranslateY);
        translateAnimation.setDuration(2000);
//        view.setAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBtnFinal.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(translateAnimation);
    }

    private void alphaAnimation(){
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_set);
//        mBtnAlpha.startAnimation(animation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //这边可以对整个动画过程做监听
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBtnAlpha.startAnimation(alphaAnimation);

//        mBtnAlpha.setBackgroundResource(R.drawable.frame_animation);
//        AnimationDrawable drawable = (AnimationDrawable) mBtnAlpha.getBackground();
//        drawable.start();
    }


    public void startScaleAnimation() {
        /**
         * ScaleAnimation第一种构造
         *
         * @param fromX X方向开始时的宽度，1f表示控件原有大小
         * @param toX X方向结束时的宽度，
         * @param fromY Y方向上开的宽度，
         * @param toY Y方向结束的宽度
         * 这里还有一个问题：缩放的中心在哪里？ 使用这种构造方法，默认是左上角的位置，以左上角为中心开始缩放
         */
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f);
        /**
         * ScaleAnimation第二种构造解决了第一种构造的缺陷， 无法指定缩放的位置
         *
         * @param fromX 同上
         * @param toX 同上
         * @param fromY 同上
         * @param toY 同上
         * @param pivotX 缩放的轴心X的位置，取值类型是float，单位是px像素，比如：X方向控件中心位置是mIvScale.getWidth() / 2f
         * @param pivotY 缩放的轴心Y的位置，取值类型是float，单位是px像素，比如：X方向控件中心位置是mIvScale.getHeight() / 2f
         */
        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1f, 2f, 1f, 2f, mIvImg.getWidth() / 2f, mIvImg.getHeight() / 2f);

        /**
         * ScaleAnimation第三种构造在第二种构造的基础上，可以通过多种方式指定轴心的位置，通过Type来约束
         *
         * @param fromX 同上
         * @param toX 同上
         * @param fromY 同上T
         * @param toY 同上
         * @param pivotXType 用来约束pivotXValue的取值。取值有三种：Animation.ABSOLUTE，Animation.RELATIVE_TO_SELF，Animation.RELATIVE_TO_PARENT
         * Type：Animation.ABSOLUTE：绝对，如果设置这种类型，后面pivotXValue取值就必须是像素点；比如：控件X方向上的中心点，pivotXValue的取值mIvScale.getWidth() / 2f
         *            Animation.RELATIVE_TO_SELF：相对于控件自己，设置这种类型，后面pivotXValue取值就会去拿这个取值是乘上控件本身的宽度；比如：控件X方向上的中心点，pivotXValue的取值0.5f
         *            Animation.RELATIVE_TO_PARENT：相对于它父容器（这个父容器是指包括这个这个做动画控件的外一层控件）， 原理同上，
         * @param pivotXValue  配合pivotXType使用，原理在上面
         * @param pivotYType 原理同上
         * @param pivotYValue 原理同上
         */
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1f, 2f, 1f, 2f, ScaleAnimation.ABSOLUTE,
                mIvImg.getWidth() / 2f, ScaleAnimation.ABSOLUTE, mIvImg.getHeight() / 2f);
        //设置动画持续时长
        scaleAnimation2.setDuration(3000);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        scaleAnimation2.setFillAfter(true);
        //设置动画结束之后的状态是否是动画开始时的状态，true，表示是保持动画开始时的状态
        scaleAnimation2.setFillBefore(true);
        //设置动画的重复模式：反转REVERSE和重新开始RESTART
        scaleAnimation2.setRepeatMode(ScaleAnimation.REVERSE);
        //设置动画播放次数
        scaleAnimation2.setRepeatCount(ScaleAnimation.INFINITE);
        //开始动画
        mIvImg.startAnimation(scaleAnimation2);
        //清除动画
//        mIvImg.clearAnimation();
        //同样cancel（）也能取消掉动画
//        scaleAnimation2.cancel();
    }

    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition();
    }
}
