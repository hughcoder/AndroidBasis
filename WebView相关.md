# WebView相关

### 1.Android 调用js的方法

```
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
```


### 2.js调用Android的代码 （3种方式）

* 通过WebView的addJavascriptInterface（）进行对象映射
* 通过 WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url
* 通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息


```
//方法一
public class AndroidtoJs extends Object {

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
        Log.e("aaa","js调用了Android的代码");
    }


}

  webView.addJavascriptInterface(new AndroidtoJs(), "test");//AndroidtoJS类对象映射到js的
  
  -------js层
    function callAndroid(){
          // 由于对象映射，所以调用test对象等于调用Android映射的对象
              test.hello("js调用了android中的hello方法");
           }
           
           

方法二

WebViewClient 通过拦截

 @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen", "拦截url:" + url);

            Uri uri = Uri.parse(url);
            // 如果url的协议 = 预先约定的 js 协议
            // 就解析往下解析参数
            if ( uri.getScheme().equals("js")) {

                // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                // 所以拦截url,下面JS开始调用Android需要的方法
                if (uri.getAuthority().equals("webview")) {

                    //  步骤3：
                    // 执行JS所需要调用的逻辑
                    Log.e("aaa","第二种通过url拦截来完成 js调用了Android的代码");
                    // 可以在协议上带有参数并传递到Android上
                    HashMap<String, String> params = new HashMap<>();
                    Set<String> collection = uri.getQueryParameterNames();

                }
                return true;
            }

//            if (url.equals("http://www.google.com/")) {
//                Toast.makeText(WebViewActivity.this, "国内不能访问google,拦截该url", Toast.LENGTH_LONG).show();
//                return true;//表示我已经处理过了
//            }
            return super.shouldOverrideUrlLoading(view, url);
        }           



-------js层

        function callAndroid(){
                 /*约定的url协议为：js://webview?arg1=111&arg2=222*/
            document.location = "js://webview?arg1=111&arg2=222";
         }
```