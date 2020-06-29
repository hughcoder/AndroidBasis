package com.hugh.ffmpegaudio;

import android.app.Activity;
import android.os.Bundle;

import com.hugh.basis.R;

import androidx.annotation.Nullable;

/**
 * Created by chenyw on 2020/6/23.
 */
public class FFmpegActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg_main);
    }
}
