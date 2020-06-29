package com.hugh.ffmpeg;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2020/6/29.
 */
public class CCMainActivity extends AppCompatActivity {

    private TextView mTvText;


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccmain);
        mTvText = findViewById(R.id.tv_text);

        mTvText.setText(stringFromJNI());

    }

    public native String stringFromJNI();



}
