package com.hugh.basis.webView.jsmodule;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by chenyw on 2019-08-30.
 */
public class AndroidScenondToJs {
    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
        Log.e("我是第二个hello",msg);
    }

}
