package com.hugh.basis.retrofit.bean;

import android.util.Log;

/**
 * Created by chenyw on 2019-07-12.
 */
public class Translation {

    public int status;
    public content content;

    public static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    public String getVerdor() {
        return content.vendor;
    }

    public void show() {
        Log.e("aaa", status + "");
        Log.e("aaa", content.from + "");
        Log.e("aaa", content.to + "");
        Log.e("aaa", content.vendor + "");
        Log.e("aaa", content.out + "");
        Log.e("aaa", content.errNo + "");

    }
}
