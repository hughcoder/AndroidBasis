package com.hugh.basis;

import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by {chenyouwei}
 * Date: {2019/4/15}
 */
public class AnimationActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);

        mButton = findViewById(R.id.btn_click);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_set);
//        mButton.startAnimation(animation);

//        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
//        alphaAnimation.setDuration(2000);
//        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                //这边可以对整个动画过程做监听
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        mButton.startAnimation(alphaAnimation);

//        mButton.setBackgroundResource(R.drawable.frame_animation);
//        AnimationDrawable drawable = (AnimationDrawable) mButton.getBackground();
//        drawable.start();

        ObjectAnimator.ofFloat(mButton, "translationY", 800).start();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofInt(mButton, "width", 500).setDuration(5000).start();
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition();
    }
}
