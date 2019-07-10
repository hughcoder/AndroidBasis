package com.hugh.basis.webView.jsmodule;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by chenyw on 2019-07-10.
 */
public class AndroidtoJs extends Object {

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
        Log.e("aaa","js调用了Android的代码");
    }


}
