package com.hugh.basis.animate;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hugh.basis.R;

public class AnimatorActivity extends AppCompatActivity {
    private Button mBtnTranslate;
    private Button mBtnWidthChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        mBtnTranslate= findViewById(R.id.btn_translate);
        mBtnWidthChange = findViewById(R.id.btn_width_change);
        mBtnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ValueAnimator.ofInt(0.5,0,40);
            }
        });
        mBtnWidthChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofInt(mBtnWidthChange, "width", 500).setDuration(5000).start();
            }
        });
    }
}
