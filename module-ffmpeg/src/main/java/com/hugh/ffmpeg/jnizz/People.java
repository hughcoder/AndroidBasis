package com.hugh.ffmpeg.jnizz;

import android.util.Log;

/**
 * Created by chenyw on 2020/6/30.
 */
public class People {

    public String name;

    public String getName() {
        Log.e("aaa","被调用了");
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
