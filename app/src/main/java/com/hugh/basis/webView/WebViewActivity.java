package com.hugh.basis.webView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hugh.basis.R;
import com.hugh.basis.webView.jsmodule.AndroidScenondToJs;
import com.hugh.basis.webView.jsmodule.AndroidtoJs;

import java.util.HashMap;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2019-07-09.
 */

//webView调用js 两种方法

//   1.webView.loadUrl("javascript:callJS()");
//   2.WebView的evaluateJavascript（） 这个方法会更加高效
public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private final String url = "https://www.baidu.com/s?wd=Android%20webView%20%E7%AE%80%E5%8D%95%E4%BD%BF%E7%94%A8&rsv_spt=1&rsv_iqid=0xca817d92002a8a5c&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=33&rsv_sug1=21&rsv_sug7=100&rsv_t=3eb7RnblWzeOqkPkn7qWtHmVpBr0YtODk%2Fd6Z4wOrDn%2FW%2BjSVSEKFBAHo3C%2FQYvoAxN0&rsv_sug2=0&inputT=8862&rsv_sug4=8862";
    private final String url2 = "https://www.baidu.com";
    private final String url3 = "file:///android_asset/a.html";
    private final String url4 ="http://www.google.com/";


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Log.e("aaa", "webView 初始化");
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progressbar);
      //  webView.setWebViewClient(new WebViewClient());//使其跳转后依然使用webview来显示
//        webView.loadUrl(url3);//利用loadUrl（）显示网址

        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.e("aaa",url);
//                if (url.contains("blog.csdn.net")){
//                    view.loadUrl("http://www.baidu.com");
//                }else {
//                    view.loadUrl(url);
//                }
//                return true;
//            }
//        });

//        webView.loadUrl("http://blog.csdn.net/harvic880925");
        webView.loadUrl(url3);


        WebSettings webSettings = webView.getSettings();
        //  webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        //    webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放


        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名

        webView.addJavascriptInterface(new AndroidScenondToJs(),"test");
        webView.addJavascriptInterface(new AndroidtoJs(), "test");//AndroidtoJS类对象映射到js的test对象

        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过Handler发送消息
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        //以下是Android 调用js代码

                        //方法一
                        // 注意调用的JS方法名要对应上
                        // 调用javascript的callJS()方法
                        webView.loadUrl("javascript:callJS()");

                        //方法二
                        webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                Log.e("aaa", value);
                            }
                        });
                    }
                });
            }
        });
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        //作用：处理各种通知 & 请求事件
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            //作用：在页面加载结束时调用。我们可以关闭loading 条，切换程序动作。
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            //作用：开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen", "拦截url:" + url);

            Uri uri = Uri.parse(url);
            // 如果url的协议 = 预先约定的 js 协议
            // 就解析往下解析参数
//            if ( uri.getScheme().equals("js")) {
//
//                // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
//                // 所以拦截url,下面JS开始调用Android需要的方法
//                if (uri.getAuthority().equals("webview")) {
//
//                    //  步骤3：
//                    // 执行JS所需要调用的逻辑
//                    Log.e("aaa","第二种通过url拦截来完成 js调用了Android的代码");
//                    // 可以在协议上带有参数并传递到Android上
//                    HashMap<String, String> params = new HashMap<>();
//                    Set<String> collection = uri.getQueryParameterNames();
//
//                }
//                return true;
//            }

            if (url.equals("http://www.google.com/")) {
                Toast.makeText(WebViewActivity.this, "国内不能访问google,拦截该url", Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        //作用：在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        //作用：加载页面的服务器出现错误时（如404）调用。
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }


    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen", "网页标题:" + title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
        }

        //作用：支持javascript的确认框
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            new AlertDialog.Builder(WebViewActivity.this)
                    .setTitle("JsConfirm")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    })
                    .setCancelable(false)
                    .show();
// 返回布尔值：判断点击时确认还是取消
// true表示点击了确认；false表示点击了取消；
            return true;
        }

        //点击确认返回输入框中的值，点击取消返回 null。

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            final EditText et = new EditText(WebViewActivity.this);
            et.setText(defaultValue);
            new AlertDialog.Builder(WebViewActivity.this)
                    .setTitle(message)
                    .setView(et)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm(et.getText().toString());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    })
                    .setCancelable(false)
                    .show();

            return true;
        }
    };

    //在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空。
    @Override
    protected void onDestroy() {
        //下面的处理是在一定程度减少了内存泄漏
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();

    }
}
