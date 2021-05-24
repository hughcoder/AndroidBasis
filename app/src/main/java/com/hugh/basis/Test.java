package com.hugh.basis;


import android.net.Uri;

public class Test {
    public static void main(String[] args) {
        String url = "http://s.m.taobao.com/inspect_breakpt_connect?server_url=http://30.10.72.132:3712";
        Uri uri = Uri.parse(url);
        System.out.println(uri.getQueryParameter("server_url"));

    }
}
