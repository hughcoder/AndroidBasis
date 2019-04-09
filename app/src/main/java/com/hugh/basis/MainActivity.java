package com.hugh.basis;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    public static String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG,"--- onSaveInstanceState");
        outState.putString("extra_test","test");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG,"onRestoreInstanceState");
        Log.e("onRestoreInstanceState",savedInstanceState.toString());
        String test = savedInstanceState.getString("extra_test");
        Log.e("onRestoreInstanceState"," test:"+test);
        //当Activity被重新创建后,onRestoreInstanceState会被调用
        //通过这个和onCreate方法来判断Activity是否被重建
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");

        if(savedInstanceState !=null){
            String test = savedInstanceState.getString("extra_test");
            Log.e(TAG,"test:"+test);
        }

        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn_goto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG,"newConfig.orientation:"+newConfig.orientation);
    }
}
