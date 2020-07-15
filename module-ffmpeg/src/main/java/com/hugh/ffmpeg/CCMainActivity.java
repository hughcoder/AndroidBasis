package com.hugh.ffmpeg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hugh.ffmpeg.jnizz.People;

import java.util.UUID;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by chenyw on 2020/6/29.
 */
public class CCMainActivity extends AppCompatActivity {

    private TextView mTvText;
    private String mTestStr = "good";
    private static int mTestStaticCount = 6;
    private static int  a = 1;
    private People xiaoming = new People();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
//        System.loadLibrary("ffmpeg_lib");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccmain);
        mTvText = findViewById(R.id.tv_text);

        mTvText.setText(stringFromJNI());
        this.accessField();
        this.accessStaticField();
        Log.e("aaa",mTestStr);
        Log.e("aaa","----->之后的数字"+mTestStaticCount);
        this.handleMethod();
        this.handleStaticMethod();

        xiaoming.setName("xiaoming");

        accessClassMethod();

        int[] arr = getArray(5);
        for (int i = 0; i < arr.length; i++) {
            Log.e("aaa","-------->"+arr[i]);
        }

        this.createGlobalRef();
        Log.e("aaa","创建getGlobalRef"+getGlobalRef());
        deleteGlobalRef();

        int i = 0;
        for (; i < 5; i++) {
            staticRef();
            NostaticRef();
        }

//        Log.e("aaa","getFFmpegVersion"+getFFmpegVersion());




    }

    public int getIntValue(){
        return 1;
    }

//    public native String getFFmpegVersion();

    public native String stringFromJNI();
    //返回静态字符串
    public static String getStaticStr() {
        return "hahahahaha";
    }
    //访问变量和静态变量
    public native void accessField();
    public native void accessStaticField();
    //访问方法 和静态方法
    public native void handleMethod();
    public native void handleStaticMethod();
    //访问对象的方法
    public native void accessClassMethod();

    //数据相关操作
    //传入数组
    public native void putArray(int[] arr);
    //返回数组
    public native int[] getArray(int arrLength);

    //全局引用
    public native void createGlobalRef();
    public native String getGlobalRef();
    public native void deleteGlobalRef();
    public native void staticRef();
    public native void NostaticRef();
}
